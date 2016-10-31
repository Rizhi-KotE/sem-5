//
// Created by rizhi-kote on 29.10.16.
//

#ifndef LAB4_SERVER_H
#define LAB4_SERVER_H


static const char *const MAX_SOCKETS_AMOUNT = "max_sockets_amount";
static const char * BUFFER_SIZE_KEY = "buffer_size";

#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <cstring>
#include <csignal>
#include "Buffer.h"

class Server {
    int message_size;
    char *message;
    int *sockets;
    int serverSocket;
    uint port;
    Buffer buffer;
    fd_set set;
    uint max_fd;
    int maxSocketAmount;
public:
    Server(uint port, sig_atomic_t *signInt);

    int runServerSocket();

    int runSocketHandler(int sock);

    ~Server();

    void acceptSocket();

    sig_atomic_t *signInt;

    void configure();
};

#endif //LAB4_SERVER_H
