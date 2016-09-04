project(test)

set(CMAKE_BUILD_TYPE Debug)

cmake_minimum_required(VERSION 2.8)
aux_source_directory(. SRC_LIST)

add_executable(${PROJECT_NAME} ${SRC_LIST})
target_link_libraries(${PROJECT_NAME} LINK_PUBLIC nw-lib)


