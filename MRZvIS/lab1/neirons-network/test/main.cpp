#define BOOST_TEST_MODULE nw-math-test
#include <nw-math.h>
#include <neiron.h>
#include <neirons-utils.h>
#include <simple_mas.h>
#include "triple.h"
#include <boost/test/included/unit_test.hpp>
#include <iostream>

triple<simple_mas<double>, simple_mas<double>, double > init_simple_vectors(){
    double *array = new double[3]{1, 2, 3};
    double *array2 = new double[3]{1, 2, 3};
    simple_mas<double> mas1(array, 3);
    simple_mas<double> mas2(array2, 3);
    return triple<simple_mas<double>, simple_mas<double>, double >(mas1, mas2, 14);
}

BOOST_AUTO_TEST_CASE (scalar_composition_test_1){
    triple<simple_mas<double>, simple_mas<double>, double> inits = init_simple_vectors();
    BOOST_CHECK(scalar_composition(inits.first.mas, inits.second.mas, inits.first.length)==inits.third);

}

BOOST_AUTO_TEST_CASE (scalar_composition_test_2){
    triple<simple_mas<double>, simple_mas<double>, double> inits = init_simple_vectors();
    BOOST_CHECK(scalar_composition(inits.first, inits.second)==inits.third);
}



BOOST_AUTO_TEST_CASE (calc_neiron_out_signal){
    triple<simple_mas<double>, simple_mas<double>, double> inits = init_simple_vectors();
    neiron<double> n = create_neiron<double>();
    set_signals(n, inits.first);
    set_weights(n, inits.second);
    BOOST_CHECK_EQUAL(inits.first.mas[0], 1);
    BOOST_CHECK_EQUAL(n.in_signals.mas[0], 1.);
    BOOST_CHECK_EQUAL(n.in_signals.mas, inits.first.mas);
    BOOST_CHECK_EQUAL(n.weights.mas, inits.second.mas);
    BOOST_CHECK_EQUAL(neiron_function(n), inits.third);
}

BOOST_AUTO_TEST_CASE (parse_neiron_test){
    const std::string str = "3 1 2 3 2";
    neiron<double> n = parse_neiron(str);

    double *mas = new double[3]{1, 2, 3};
    double *weights = n.weights.mas;
    double step = 2;
    BOOST_CHECK_EQUAL_COLLECTIONS(weights, weights+3, mas, mas+3);
    BOOST_CHECK_EQUAL(n.porog, step);
}

BOOST_AUTO_TEST_CASE (load_neiron_test){
    const char *str = "3 1 2 3 2";
    neiron<double> n = load_neiron("/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/neirons-network/test/neiron.txt");

    double *mas = new double[3]{1, 2, 3};
    double *weights = n.weights.mas;
    double step = 2;
    BOOST_CHECK_EQUAL_COLLECTIONS(weights, weights+3, mas, mas+3);
    BOOST_CHECK_EQUAL(n.porog, step);
}

BOOST_AUTO_TEST_CASE (encode_neiron_test){
    const char *str = "3 1 2 3 2";
    neiron<double> n = parse_neiron(str);

    std::string result = encode_neiron(n);
    BOOST_CHECK_EQUAL(str, result);
}

BOOST_AUTO_TEST_CASE (save_neiron_test){
    const char *str = "3 1 2 3 2";
    neiron<double> n = parse_neiron(str);

    save_neiron("neiron_temp.txt", n);


    neiron<double> result = load_neiron("neiron_temp.txt");
    BOOST_CHECK_EQUAL(encode_neiron(result), str);
}

