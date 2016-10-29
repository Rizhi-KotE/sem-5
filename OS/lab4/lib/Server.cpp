#include <pthread.h>
#include "Server.h"
#include <arpa/inet.h>

int Server::runServerSocket(uint port) {
    int err = listen(serverSocket, 3);
    timeval timeout;
    timeout.tv_usec = 0;
    while (true) {
        timeout.tv_sec = 60;
        fd_set readenSocks = set;
        fd_set expSocket = set;
        printf("[%d]: idle\n", pthread_self());
        int activity = select(max_fd, &readenSocks, NULL, &set, &timeout);
        if (activity < 0) {
            printf("error: %s", errno);
        }
        if (FD_ISSET(serverSocket, &readenSocks)) {
            sockaddr_in cli_addr;
            unsigned int clen = sizeof(cli_addr);
            int newsock = accept(serverSocket, (sockaddr *) &cli_addr, &clen);
            if (newsock < 0) {
                sprintf(message, "[%d]: accept failed\n", pthread_self());
                buffer.append(message, strlen(message));
                printf(message);
            }
            for (int i = 0; i < 10; i++)
                if (sockets[i] == 0) {
                    sockets[i] = newsock;
                    break;
                }
            FD_SET(newsock, &set);
        }
        for (int i = 0; i < 10; i++)
            if (sockets[i] != 0 && FD_ISSET(sockets[i], &readenSocks))
                runSocketHandler(sockets[i]);
        if (FD_ISSET(STDIN_FILENO, &readenSocks)) {
            read(STDIN_FILENO, message, BUF_SIZE);
            if (strcmp(message, "stop") == 0) {
                break;
            }
        }
    }
    return 0;
}

int Server::runSocketHandler(int socket) {
    unsigned int messageSize;
    if (socket < 0) {
        sprintf(message, "[%d]: accept failed\n", pthread_self());
        buffer.append(message, strlen(message));
        printf(message);
        return socket;
    }
    sockaddr_in addr;
    unsigned int sockLen;
    getsockname(sockLen, (sockaddr *) &addr, &sockLen);
    sprintf(message, "[%d]: accept new client %s\n", pthread_self(), inet_ntoa(addr.sin_addr));
    buffer.append(message, strlen(message));
    printf(message);
    messageSize = read(socket, message, BUF_SIZE);
    message[messageSize++] = '\0';
    write(socket, message, messageSize);

}

Server::~Server() {
    printf("destruct server");
    close(serverSocket);
}

Server::Server(uint port, Buffer &buffer) {
    this->buffer = buffer;
    this->port = port;
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

