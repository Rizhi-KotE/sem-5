#ifndef PROJECT_BUFFER_H
#define PROJECT_BUFFER_H


class Buffer {
    unsigned int maxSize = 50;
    unsigned int size = 0;
    char **buffer;
    char *filename = "/home/rizhi-kote/Student/sem-5/OS/lab4/lib/buffer.tmp";

    void resize();
public:
    Buffer();

    void append(char const *string, unsigned int length);

    int backup();

    ~Buffer();
};


#endif //PROJECT_BUFFER_H
