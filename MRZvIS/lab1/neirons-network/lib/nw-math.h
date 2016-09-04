#ifndef MATH_H
#define MATH_H

#include "simple_mas.h"

template <typename T, typename E>
double scalar_composition(T *mas1, E *mas2,int length){
    double sum = 0;
    for(int i = 0; i < length; i++){
        sum += mas1[i]*mas2[i];
    }
    return sum;
}

template <typename T, typename E>
double scalar_composition(simple_mas<T> vector1, simple_mas<E> vector2){
    if(vector1.length!=vector2.length){
        throw -1;
    }
    return scalar_composition(vector1.mas, vector2.mas, vector1.length);
}

#endif // MATH_H
