//
// Created by rizhi-kote on 15.11.16.
//

#ifndef PROJECT_CONSTANTS_H
#define PROJECT_CONSTANTS_H

static const char *const BACKUP_MESSAGE = "[backup] file: %s\n";
static const char *const PROC_SELF = "/proc/self/fd/%d";
static const char *const DISCONNECT_MESSAGE = "[%lu]: [client %s] [disconnected]\n";
static const char *const CLIENT_MESSAGE = "[%lu]: [client %s] [message] %s\n";
static const char *const ACCEPT_CLIENT_MESSAGE = "[%lu]: accept new client %s\n";
static const char *const MAX_SOCKETS_AMOUNT = "max_sockets_amount";
static const char *const ACCEPT_FAILED_MESSAGE = "[%lu]: accept failed\n";
static const char *const IDLE_MESSAGE = "[%lu]: idle\n";
static const char * BUFFER_SIZE_KEY = "buffer_size";

#endif //PROJECT_CONSTANTS_H
