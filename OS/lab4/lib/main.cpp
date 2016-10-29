
#include <cstdio>
#include "Server.h"
#include "Buffer.h"

#define BUF_SIZE 256

Buffer buffer;

void sigIntHandler(){
    buffer.backup();
    exit(0);
}

int main(int argc, char **argv) {
    if (argc < 2)
    {
        fprintf(stderr,"usage: %s <port_number>\n", argv[0]);
        return EXIT_FAILURE;
    }
    uint port = atoi(argv[1]);
    Server server(port, buffer);
    server.runServerSocket(port);
    printf("error %d, %s\n", errno, strerror(errno));
    return 0;
}