//
// Created by rizhi-kote on 29.10.16.
//

#include <cstring>
#include "Buffer.h"
#include <fcntl.h>
#include <unistd.h>
#include <cstdio>
#include <errno.h>

Buffer::Buffer() {
    buffer = new char *[50];
}

void Buffer::append(char const *string, unsigned int length) {
    if (size >= maxSize) {
        resize();
    }
    char *newString = new char[length];
    memcpy(newString, string, length);
    buffer[size] = newString;
    write(STDOUT_FILENO, newString, length);
    size++;
}

Buffer::~Buffer() {
    backup();
    printf("destruct\n");
    for (int i = 0; i < size; i++) delete[] buffer[i];
    delete[] buffer;
}

int Buffer::backup() {
    printf("backup\n");
    int file = open(filename, O_RDWR | O_CREAT, S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH);
    if (file == -1) {
        return file;
    }
    for (int i = 0; i < size; i++) {
        for (int j = 0; buffer[i][j]; write(file, &buffer[i][j++], 1));
        delete[] buffer[i];
    }
    size = 0;
    int err = close(file);
    return err;
}

void Buffer::resize() {
    char **newBuffer = new char *[maxSize * 2];
    maxSize *= 2;
    memcpy(newBuffer, buffer, sizeof(buffer));
    buffer = newBuffer;
}
