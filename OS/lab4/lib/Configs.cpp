//
// Created by rizhi-kote on 31.10.16.
//

#include "Configs.h"
#include <cstdio>

Configs Configs::instance;

const char *Configs::get(string key) {
    return instance.configs.find(key) != instance.configs.end() ? instance.configs.find(key)->second.c_str() : NULL;
}

Configs::Configs() {
    char *key, *value;
    key = new char[256];
    value = new char[256];
    FILE *file = fopen("./configs.txt", "r");
    if (!file) {
        printf("file is not open");
        return;
    }
    while (file && fscanf(file, "%s = %s", key, value) >= 0) {
        configs.insert(pair<string, string>(key, value));
    };
    return;
}


