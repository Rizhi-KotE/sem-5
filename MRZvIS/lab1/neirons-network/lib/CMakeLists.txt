aux_source_directory(. LIB_SRC)
add_library( nw-lib ${LIB_SRC} ${HEADERS})

target_include_directories(nw-lib PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})

set(CMAKE_BUILD_TYPE Debug)
