//
// Created by rizhi-kote on 29.10.16.
//

#include <cstring>
#include "Buffer.h"
#include <fcntl.h>
#include <unistd.h>

Buffer::Buffer() {
    buffer = new char *[50];
}

void Buffer::append(char const *string, unsigned int length) {
    if (size >= maxSize) {
        resize();
    }
    char *newString = new char[length];
    memcpy(newString, string, length);
    buffer[size++] = newString;
}

Buffer::~Buffer() {
    for (int i = 0; i < size; i++) delete[] buffer[i];
    delete[] buffer;
}

int Buffer::backup() {
    int file = open(filename, O_RDWR | O_CREAT);
    if (file == -1) {
        return file;
    }
    for (int i = 0; i < size; i++)
        for (int j = 0; buffer[i][j]; write(file, &buffer[i][j++], 1));
    return close(file);
}

void Buffer::resize() {
    char **newBuffer = new char *[maxSize *= 2];
    memcpy(newBuffer, buffer, size);
    buffer = newBuffer;
}
