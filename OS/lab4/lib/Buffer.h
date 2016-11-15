#ifndef PROJECT_BUFFER_H
#define PROJECT_BUFFER_H


#include <memory>
#include <vector>

using namespace std;

static const int FIRST_BUF_SIZE = 5;

static const int BUFFER_SIZE = 256;
static const char * DUMP_FILE_KEY = "dump_file";

class Buffer {
    unsigned int maxSize;
    unsigned int size = 0;
    vector<string> buffer;
    const char *filename;
    bool verbose;
public:
    Buffer();

    void append(std::string &string);

    int backup();

    ~Buffer();

    void configure();
};


#endif //PROJECT_BUFFER_H
