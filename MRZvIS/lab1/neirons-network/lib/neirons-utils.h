#ifndef NEIRONSUTILS_H
#define NEIRONSUTILS_H
#include "neiron.h"
#include <string>

neiron<double> parse_neiron(const std::string str);

std::string encode_neiron(neiron<double> );

bool save_neiron(const std::string filename, neiron<double>);

neiron<double> load_neiron(const std::string filename);

template<typename T>
std::string encode_vector(vector<T> mas);

template<typename T>
std::string encode_vector(T *mas, int length);


template<typename T>
vector<T> parse_vector(const char *str);

template<typename T>
vector<T> parse_vector(std::string str);

template <typename T>
T **parse_matrix(std::string str);

template <typename T>
std::string encode_matrix(T **matrix, int width, int height);

std::string read_file(std::string filename);

void write_file(std::string filename, std::string content);
#endif // NEIRONSUTILS_H
