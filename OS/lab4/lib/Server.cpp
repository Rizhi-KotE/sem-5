#include <pthread.h>
#include "Server.h"
#include <arpa/inet.h>

int Server::runServerSocket(uint port) {
    int err = listen(serverSocket, 3);
    timespec timeout;
    timeout.tv_nsec = 0;
    sigset_t newset;
    sigemptyset(&newset);
    sigaddset(&newset, SIGINT);
    while (true) {
        timeout.tv_sec = 1;
        fd_set readenSocks = set;
        sprintf(message, "[%d]: idle\n\0", pthread_self());
        buffer.append(message, strlen(message));
        int activity = pselect(max_fd, &readenSocks, NULL, NULL, &timeout, &newset);
        if (activity < 0) {
            printf("error: %s", errno);
        }
        if (FD_ISSET(serverSocket, &readenSocks)) acceptSocket();

        for (int i = 0; i < 10; i++)
            if (sockets[i] != 0 && FD_ISSET(sockets[i], &readenSocks))
                runSocketHandler(sockets[i]);

        if (FD_ISSET(STDIN_FILENO, &readenSocks)) {
            int readenSize = read(STDIN_FILENO, message, BUF_SIZE);
            message[readenSize] = '\0';
            if (readenSize < 0) {
                continue;
            }
            if (strcmp(message, "stop\n") == 0) {
                buffer.backup();
                break;
            }
        }
        if (*signInt) {
            buffer.backup();
            break;
        }
    }
    return 0;
}

void Server::acceptSocket() {
    sockaddr_in cli_addr;
    unsigned int clen = sizeof(cli_addr);
    int newsock = accept(serverSocket, (sockaddr *) &cli_addr, &clen);
    if (newsock < 0) {
        sprintf(message, "[%d]: accept failed\n\0", pthread_self());
        buffer.append(message, strlen(message));
        printf(message);
    } else {
        sockaddr_in addr;
        unsigned int sockLen;
        getsockname(sockLen, (sockaddr *) &addr, &sockLen);
        sprintf(message, "[%d]: accept new client %s\n\0", pthread_self(), inet_ntoa(addr.sin_addr));
        buffer.append(message, strlen(message));
    }
    for (int i = 0; i < 10; i++)
        if (sockets[i] == 0) {
            sockets[i] = newsock;
            break;
        }
    if (max_fd < newsock + 1) {
        max_fd = newsock + 1;
    }
    FD_SET(newsock, &set);
}

int Server::runSocketHandler(int socket) {
    unsigned int messageSize;
    messageSize = read(socket, message, BUF_SIZE);
    if (messageSize == 0) {
        sockaddr_in addr;
        unsigned int sockLen;
        getsockname(sockLen, (sockaddr *) &addr, &sockLen);
        for (int i = 0; i < 10; i++) if (sockets[i] == socket) sockets[i] = 0;
        FD_CLR(socket, &set);
        sprintf(message, "[%d]: client %s disconnected\n\0", pthread_self(), inet_ntoa(addr.sin_addr));
        buffer.append(message, strlen(message));
        close(socket);
    } else {
        buffer.append(message, messageSize);
        write(socket, message, messageSize);
    }
    return 0;
}

Server::~Server() {
    printf("destruct server\n");
    close(serverSocket);
}

Server::Server(uint port, sig_atomic_t *signInt) {
    this->port = port;
    this->signInt = signInt;
    serverSocket = socket(AF_INET, SOCK_STREAM, 0);
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
    FD_ZERO(&set);
    FD_SET(serverSocket, &set);
    FD_SET(STDIN_FILENO, &set);
    max_fd = serverSocket + 1;
    memset(sockets, 0, sizeof(sockets));
}

