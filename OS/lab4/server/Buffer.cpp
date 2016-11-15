//
// Created by rizhi-kote on 29.10.16.
//

#include <cstring>
#include "Buffer.h"
#include "Configs.h"
#include "constants.h"
#include <fcntl.h>
#include <unistd.h>
#include <cstdio>
#include <cstdlib>
#include <errno.h>

Buffer::Buffer() {
    configure();
}

void Buffer::append(string &str) {
    buffer.push_back(str);
    if (verbose) {
        write(STDOUT_FILENO, str.c_str(), str.size());
    }
}

Buffer::~Buffer() {
    backup();
}

int Buffer::backup() {
    int file = open(filename, O_RDWR | O_CREAT | O_APPEND, S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH);

    if (file == -1) {//  проверка на открытие файла
        return file;
    }
    for (vector<string>::iterator it = buffer.begin(); it != buffer.end(); it++) {
        write(file, (*it).c_str(), (*it).size());
    }
    buffer.clear();

    char filepath[BUFFER_SIZE + 1];
    sprintf(filepath, PROC_SELF, file);
    size_t readen = readlink(filepath, filepath, BUFFER_SIZE);
    filepath[readen] = '\0';
    printf(BACKUP_MESSAGE, filepath);
    int err = close(file);
    return err;
}

void Buffer::configure() {
    filename = Configs::get(DUMP_FILE_KEY) ? Configs::get(DUMP_FILE_KEY) : "./buffer.tmp";
    verbose = Configs::get("verbose") ? atoi(Configs::get("verbose")) : 1;
}
