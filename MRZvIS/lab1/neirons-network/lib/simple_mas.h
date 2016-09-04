#ifndef SIMPLE_MAS
#define SIMPLE_MAS

template <typename T>
struct simple_mas{
    T *mas;
    int length;

    simple_mas(){}

    simple_mas(T *mas, int length){
        set_mas(mas, length);
    }

    void set_mas(T *mas, int length);
};

template<typename T> void simple_mas<T>::set_mas(T *mas, int length){
    this->mas = mas;
    this->length = length;
}

#endif
