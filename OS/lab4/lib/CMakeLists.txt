project(server)

set(CMAKE_BUILD_TYPE Debug)

set(SOURCE_FILES main.cpp Server.cpp Server.h Buffer.cpp Buffer.h Configs.cpp Configs.h)
add_executable(server ${SOURCE_FILES})