#ifndef PROJECT_BUFFER_H
#define PROJECT_BUFFER_H


static const int FIRST_BUF_SIZE = 5;

static const int BUFFER_SIZE = 256;
static const char * DUMP_FILE_KEY = "dump_file";

class Buffer {
    unsigned int maxSize;
    unsigned int size = 0;
    char **buffer;
    const char *filename;

    void resize();
    bool verbose;
public:
    Buffer();

    void append(char const *string, unsigned int length);

    int backup();

    ~Buffer();

    void configure();
};


#endif //PROJECT_BUFFER_H
