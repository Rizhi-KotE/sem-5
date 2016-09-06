#include "neirons-utils.h"
#include "neiron.h"
#include <sstream>
#include <iostream>
#include <fstream>

using namespace std;

template <typename T>
vector<T> parse_vector(const char *str){
    stringstream ss(str);
    int size;
        ss >> size;
    T *mas = new T[size];
    for(int i = 0; i < size && ss; i++){
        ss >> mas[i];
    }
    return vector<T>(mas, size);
}

template <typename T>
vector<T> parse_vector(string str){
    return parse_vector<T>(str.c_str());
}



template <typename T>
void parse_weights(neiron<T> &n, stringstream &ss){
    int size;
    if(ss){
        ss >> size;
    }
    vector<T> mas(size);
    for(int i = 0; i < size && ss; i++){
        T number;
        ss >> number;
        mas[i] = number;
    }
    set_weights(n, mas);
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

template <typename T>
string encode_vector(T *mas, int length){
    vector<T> v(mas);
    return encode_vector(v);
}

template <typename T>
string encode_vector(vector<T> mas){
    stringstream ss;
    ss << mas.size();
    for(int i = 0; i < mas.size(); i++){
        ss << " " << mas[i];
    }
    return ss.str();
}

string encode_neiron(neiron<double> n){
    stringstream ss;
    ss << encode_vector(n.weights);
    ss << " " << n.porog;
    return ss.str();
}

bool save_neiron(const string filename, neiron<double> n){
    string str = encode_neiron(n);
    ofstream F(filename.c_str());
    F << str.c_str();
    return true;
}

template <typename T>
T **parse_matrix(string str){
    stringstream ss(str);
    int length = ss.get();
    T **matrix = new T*[length];
    int i = 0;
    while(ss){
        string line;
        std::getline(ss, line);
        vector<T> mas = parse_vector<T>(line);
        matrix[i] = mas.mas;
        i++;
    }
    return matrix;
}

template <typename T>
string encode_matrix(T **matrix, int width, int height){
    stringstream ss;
    ss << height;
    for(int i = 0; i < height; i++){
        ss << encode_vector(matrix[i], width);
    }
    return ss.str();
}

std::string read_file(std::string filename){
    ifstream F(filename.c_str());
    stringstream ss;
    while(F){
        char simbol = F.get();
        ss << simbol;
    }
    return ss.str();
}

void write_file(std::string filename, std::string content){
    ofstream F(filename.c_str());
    F << content;
}
