//
// Created by rizhi-kote on 31.10.16.
//

#ifndef PROJECT_CONFIGS_H
#define PROJECT_CONFIGS_H

#include <map>
#include <string>

using namespace std;

class Configs {
    map<string, string> configs;
    void load();

    static Configs instance;
public:
    Configs();
    static const char *get(string key);
};

#endif //PROJECT_CONFIGS_H
