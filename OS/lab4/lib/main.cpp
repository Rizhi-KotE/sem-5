
#include <cstdio>
#include <signal.h>
#include "Server.h"

#define BUF_SIZE 256

sig_atomic_t signInt = 0;

void sigIntHandler(int signal) {
    signInt = 1;
}

int main(int argc, char **argv) {
    if (argc < 2) {
        fprintf(stderr, "usage: %s <port_number>\n", argv[0]);
        return EXIT_FAILURE;
    }
    struct sigaction sa;
    sa.sa_handler = sigIntHandler;
    sigaction(SIGINT, &sa, 0);
    uint port = atoi(argv[1]);
    Server server(port, &signInt);
    server.runServer();
    return 0;
}
