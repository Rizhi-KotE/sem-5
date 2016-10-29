//
// Created by rizhi-kote on 29.10.16.
//

#ifndef LAB4_SERVER_H
#define LAB4_SERVER_H


#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <cstring>
#include "Buffer.h"

class Server {
    static const int BUF_SIZE = 256;
    char message[BUF_SIZE + 1];
    int serverSocket;
    uint port;
    Buffer buffer;
    fd_set set;
    int sockets[10];
    uint max_fd;
public:
    Server(uint port, Buffer &buffer);

    int runServerSocket(uint port);

    int runSocketHandler(int sock);

    ~Server();
};

#endif //LAB4_SERVER_H
