#ifndef MATH_H
#define MATH_H

#include <vector>
using namespace std;


template <typename T, typename E>
double scalar_composition(vector<T> mas1, vector<E> mas2){
    double sum = 0;
    for(int i = 0; i < mas1.size(); i++){
        sum += mas1[i]*mas2[i];
    }
    return sum;
}

#endif // MATH_H
