//
// Created by rizhi-kote on 29.10.16.
//

#include <cstring>
#include "Buffer.h"
#include "Configs.h"
#include <fcntl.h>
#include <unistd.h>
#include <cstdio>
#include <cstdlib>
#include <errno.h>

Buffer::Buffer() {
    configure();
    maxSize = FIRST_BUF_SIZE;
    buffer = new char *[maxSize];
}

void Buffer::append(char const *string, unsigned int length) {
    if (size >= maxSize) {
        resize();
    }
    char *newString = new char[length];
    memcpy(newString, string, length);
    buffer[size] = newString;
    if (verbose) {
        write(STDOUT_FILENO, newString, length);
    }
    size++;
}

Buffer::~Buffer() {
    backup();
    delete[] buffer;
}

int Buffer::backup() {
    int file = open(filename, O_RDWR | O_CREAT | O_APPEND, S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH);

    if (file == -1) {//  проверка на открытие файла
        printf(strerror(errno));
        return file;
    }
    for (int i = 0; i < size; i++) {
        write(file, buffer[i], strlen(buffer[i]));
        delete[] buffer[i];
    }
    size = 0;

    char filepath[BUFFER_SIZE + 1];
    sprintf(filepath, "/proc/self/fd/%d", file);
    size_t readen = readlink(filepath, filepath, BUFFER_SIZE);
    filepath[readen] = '\0';
    printf("[backup] file: %s\n", filepath);
    int err = close(file);
    return err;
}

void Buffer::resize() {
    printf("resize\n");
    char **newBuffer = new char *[maxSize * 2];
    memset(newBuffer, 0, sizeof(char **) * maxSize * 2);
    memcpy(newBuffer, buffer, sizeof(char **) * maxSize);
    maxSize *= 2;
    buffer = newBuffer;
}

void Buffer::configure() {
    filename = Configs::get(DUMP_FILE_KEY) ? Configs::get(DUMP_FILE_KEY) : "./buffer.tmp";
    verbose = Configs::get("verbose") ? atoi(Configs::get("verbose")) : 1;
}
