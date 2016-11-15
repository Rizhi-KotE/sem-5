#include <pthread.h>
#include "Server.h"
#include "Configs.h"
#include "constants.h"
#include <arpa/inet.h>
#include <fcntl.h>
#include <cstring>

int Server::runServer() {
    serverSocketCreate();
    timespec timeout;
    timeout.tv_nsec = 0;
    sigset_t newset;
    sigemptyset(&newset);
    sigaddset(&newset, SIGINT);
    while (true) {
        timeout.tv_sec = 1;
        fd_set readenSocks = set;
        sprintf(message, IDLE_MESSAGE, pthread_self());
        string to_buffer(message);
        buffer.append(to_buffer);
        int activity = pselect(max_fd, &readenSocks, NULL, NULL, &timeout, &newset);
        if (activity < 0) {
            printf("error: %s", strerror(errno));
        }
        performReadFromServerSocket(readenSocks);
        permormReadableSockets(readenSocks);
        if (*signInt) {
            buffer.backup();
            break;
        }
    }
    return 0;
}

void Server::performReadFromServerSocket(fd_set &readenSocks) {
    if (FD_ISSET(serverSocket, &readenSocks)) acceptSocket();
}

void Server::permormReadableSockets(fd_set &readenSocks) {
    for (int i = 0; i < sockets.size(); i++)
        if (sockets[i] != 0 && FD_ISSET(sockets[i], &readenSocks))
            runSocketHandler(sockets[i]);
}

void Server::acceptSocket() {
    sockaddr_in cli_addr;
    unsigned int clen = sizeof(cli_addr);
    bool nextStep = true;
    while (nextStep) {
        nextStep = false;
        int newsock = accept(serverSocket, (sockaddr *) &cli_addr, &clen);
        if (errno == EWOULDBLOCK) {
            errno = 0;
            break;
        } else {
            nextStep = true;
        }
        if (newsock < 0) {
            sprintf(message, ACCEPT_FAILED_MESSAGE, pthread_self());
            string to_buffer(message);
            buffer.append(to_buffer);
        } else {
            sockaddr_in addr;
            unsigned int sockLen;
            getsockname(newsock, (sockaddr *) &addr, &sockLen);

            sprintf(message, ACCEPT_CLIENT_MESSAGE, pthread_self(), inet_ntoa(addr.sin_addr));
            string to_buffer(message);
            buffer.append(to_buffer);
        }
        sockets.push_back(newsock);
        if (max_fd < newsock + 1) {
            max_fd = newsock + 1;
        }
        FD_SET(newsock, &set);
    }
}

int Server::runSocketHandler(int socket) {
    unsigned int messageSize;
    messageSize = read(socket, message, message_size);
    if (messageSize == 0) {
        sockaddr_in addr;
        memset(&addr, 0, sizeof(addr));
        unsigned int sockLen;
        getsockname(socket, (sockaddr *) &addr, &sockLen);
        for (vector<int>::iterator it = sockets.begin(); it < sockets.end(); it++)
            if (*it == socket) {
                sockets.erase(it);
                break;
            }
        FD_CLR(socket, &set);
        sprintf(message, DISCONNECT_MESSAGE, pthread_self(), inet_ntoa(addr.sin_addr));
        string to_buffer(message);
        buffer.append(to_buffer);
        close(socket);
    } else {
        string to_buffer(message);
        buffer.append(to_buffer);
        write(socket, message, messageSize);
    }
    return 0;
}

Server::~Server() {
    close(serverSocket);
}

void Server::serverSocketCreate() {
    this->port = port;
    this->signInt = signInt;
    serverSocket = socket(AF_INET, SOCK_STREAM, 0);
    fcntl(serverSocket, F_SETFL, SOCK_NONBLOCK);
    if (serverSocket < 0) {
        printf("socket() failed: %d\n", errno);
        throw EXIT_FAILURE;
    }
    sockaddr_in serv_addr;
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(port);
    if (bind(serverSocket, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0) {
        printf("bind() failed: %d\n", errno);
        throw EXIT_FAILURE;
    }
    int err = listen(serverSocket, maxSocketAmount);
    FD_SET(serverSocket, &set);
    max_fd = serverSocket + 1;
}

Server::Server(uint port, sig_atomic_t *signInt) {
    configure();
    message = new char[message_size + 1];
    memset(message, 0, message_size + 1);
    FD_ZERO(&set);
    this->signInt = signInt;
    this->port = port;
}

void Server::configure() {
    message_size = Configs::get(BUFFER_SIZE_KEY) ? atoi(Configs::get(BUFFER_SIZE_KEY)) : 256;
    maxSocketAmount = Configs::get(MAX_SOCKETS_AMOUNT) ? atoi(Configs::get(MAX_SOCKETS_AMOUNT)) : 15;
}

