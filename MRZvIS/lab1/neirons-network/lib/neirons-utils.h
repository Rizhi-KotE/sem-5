#ifndef NEIRONSUTILS_H
#define NEIRONSUTILS_H
#include "neiron.h"
#include <string>

neiron<double> parse_neiron(const std::string str);

std::string encode_neiron(neiron<double> );

bool save_neiron(const std::string filename, neiron<double>);

neiron<double> load_neiron(const std::string filename);

#endif // NEIRONSUTILS_H
