#include "neirons-utils.h"
#include "neiron.h"
#include <sstream>
#include <iostream>
#include <fstream>

using namespace std;

template <typename T>
void parse_weights(neiron<T> &n, stringstream &ss){
    int size;
    if(ss){
        ss >> size;
    }
    T *mas = new T[size];
    for(int i = 0; i < size && ss; i++){
        ss >> mas[i];
    }
    set_weights(n, simple_mas<T>(mas, size));
}

template <typename T>
void parse_step(neiron<T> &n, stringstream &ss){
    T step;
    if(ss){
        ss >> step;
    }
    set_step(n, step);
}

template <typename T>
void parse_weights_and_step(neiron<T> &n,const string str){
    stringstream ss(str);
    parse_weights(n, ss);
    parse_step(n, ss);
}

neiron<double> parse_neiron(const string str){
    neiron<double> n = create_neiron<double>();
    parse_weights_and_step(n, str);
    return n;
}

neiron<double> load_neiron(const string filename){
    ifstream F(filename.c_str());
    string s;
    while(F){
        s.push_back(F.get());
    }
    return parse_neiron(s.c_str());
}

string encode_neiron(neiron<double> n){
    stringstream ss;
    ss << n.weights.length;
    for(int i = 0; i < n.weights.length; i++){
        ss << " " << n.weights.mas[i];
    }
    ss << " " << n.porog;
    return ss.str();
}

bool save_neiron(const string filename, neiron<double> n){
    string str = encode_neiron(n);
    ofstream F(filename.c_str());
    F << str.c_str();
    return true;
}
