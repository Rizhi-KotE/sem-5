#ifndef TRIPLE_H
#define TRIPLE_H

#include <vector>

using namespace std;
template<typename T, typename E, typename D>
struct triple{
    T first;
    E second;
    D third;

    triple(T first){
        this->first = first;
    }

    triple(T first, E second){
        this->first = first;
        this->second = second;
    }

    triple(T first, E second, D third){
        this->first = first;
        this->second = second;
        this->third = third;
    }
};

#endif // TRIPLE_H
