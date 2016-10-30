project(client)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES client.cpp)
add_executable(client.cpp ${SOURCE_FILES})