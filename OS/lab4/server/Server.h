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
#include <string>
#include <csignal>
#include "Buffer.h"

using namespace std;

class Server {
    int message_size;
    char *message;
    vector<int> sockets;
    int serverSocket;
    uint port;
    Buffer buffer;
    fd_set set;
    uint max_fd;
    int maxSocketAmount;
public:
    Server(uint port, sig_atomic_t *signInt);

    int runServer();

    int runSocketHandler(int sock);

    ~Server();

    void acceptSocket();

    sig_atomic_t *signInt;

    void configure();

    void serverSocketCreate();

    void permormReadableSockets(fd_set &readenSocks);

    void performReadFromServerSocket(fd_set &readenSocks);
};

#endif //LAB4_SERVER_H
