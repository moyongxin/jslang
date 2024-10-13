cmake_minimum_required(VERSION 3.25)

project(jslang)

option(SLANG_PATH "Path to SLANG" ${CMAKE_CURRENT_SOURCE_DIR}/slang)

set(CMAKE_EXPORT_COMPILE_COMMANDS ON)
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_program(Python_EXECUTABLE python)
find_file(SLANG_HEADER slang.h REQUIRED PATHS ${SLANG_PATH}/include)
find_library(SLANG_LIB slang REQUIRED PATHS ${SLANG_PATH}/lib)

if(WIN32)
    find_file(SLANG_DLL slang.dll REQUIRED PATHS ${SLANG_PATH}/bin)
endif(WIN32)

if(MINGW)
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS}")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS}")
    set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -static -static-libgcc -static-libstdc++")
endif(MINGW)

if(NOT Python_EXECUTABLE)
    message(FATAL_ERROR "Python not found")
endif(NOT Python_EXECUTABLE)

add_custom_target(
    jslang_extract
    COMMAND "python" "${CMAKE_CURRENT_SOURCE_DIR}/jslang_extract.py" "${SLANG_HEADER}" -o "${CMAKE_CURRENT_SOURCE_DIR}/../src/main/java/moyongxin/jslang/generated/"
    WORKING_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}
)

add_library(jslang SHARED ${CMAKE_CURRENT_SOURCE_DIR}/jslang.cpp ${CMAKE_CURRENT_SOURCE_DIR}/jni_utils.cpp)
add_dependencies(jslang jslang_extract)

target_include_directories(jslang PRIVATE ${SLANG_PATH}/include ${CMAKE_CURRENT_SOURCE_DIR}/include $ENV{JAVA_HOME}/include $ENV{JAVA_HOME}/include/win32 $ENV{JAVA_HOME}/include/linux)
target_link_libraries(jslang PRIVATE ${SLANG_LIB})

install(TARGETS jslang DESTINATION .)

if(WIN32)
    install(FILES ${SLANG_DLL} DESTINATION .)
endif(WIN32)
