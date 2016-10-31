
#include <cstdio>
#include <signal.h>
#include "Server.h"

#define BUF_SIZE 256

sig_atomic_t signInt = 0;

void sigIntHandler(int signal) {
    signInt = 1;
}

int main(int argc, char **argv) {
    printf(argv[0]);
    if (argc < 2) {
        fprintf(stderr, "usage: %s <port_number>\n", argv[0]);
        return EXIT_FAILURE;
    }
    struct sigaction sa;
    sa.sa_handler = sigIntHandler;
    sigaction(SIGINT, &sa, 0);
    uint port = atoi(argv[1]);
    Server server(port, &signInt);
    server.runServerSocket();
    return 0;
}
