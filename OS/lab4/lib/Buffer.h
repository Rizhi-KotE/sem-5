#ifndef PROJECT_BUFFER_H
#define PROJECT_BUFFER_H


class Buffer {
    unsigned int maxSize = 50;
    char **buffer;
    char size = 0;
    char *filename = "buffer.tmp";

    void resize();
public:
    Buffer();

    void append(char const *string, unsigned int length);

    int backup();

    ~Buffer();
};


#endif //PROJECT_BUFFER_H
