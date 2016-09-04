#ifndef NEIRONCACHED_H
#define NEIRONCACHED_H
#include <nw-math.h>
#include <simple_mas.h>

template <typename T>
struct neiron
{
    simple_mas<T> in_signals;
    simple_mas<T> weights;

    bool is_out_signal_calcuated;
    T out_signal_value;
    T porog;
    T (*sinaptic_function)(simple_mas<T>, simple_mas<T>);
    T (*activation_function)(T , T);
    T (*neiron_function)(neiron);
};

template<typename T>
void set_signals(neiron<T> &n, simple_mas<T> mas){
    n.in_signals = mas;
    n.is_out_signal_calcuated = false;
}

template<typename T>
void set_weights(neiron<T> &n, simple_mas<T> mas){
    n.weights = mas;
    n.is_out_signal_calcuated = false;
}

template<typename T>
void set_step(neiron<T> &n, T step){
    n.porog = step;
    n.is_out_signal_calcuated = false;
}

template<typename T>
int neiron_function(neiron<T> &n){
    if(n.is_out_signal_calcuated){
        return n.out_signal_value;
    }
    T value = n.sinaptic_function(n.in_signals, n.weights);
    n.out_signal_value = n.activation_function(value, n.porog);
    return n.out_signal_value;
}

template<typename T>
T sinaptic_function(simple_mas<T> s, simple_mas<T> w){
    return scalar_composition(s, w);
}

template<typename T>
T activation_function(T sinaptic_function_value, T porog){
    if(sinaptic_function_value<porog){
        return 0;
    }
    return sinaptic_function_value;
}

template<typename T>
neiron<T> create_neiron(){
    neiron<T> n;
    n.activation_function = &activation_function;
    n.sinaptic_function = &sinaptic_function;
    return n;
}

#endif // NEIRONCACHED_H