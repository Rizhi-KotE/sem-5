# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 2.8

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list

# Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake

# The command to remove a file.
RM = /usr/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/rizhi-kote/Student/sem-5/OS/lab4

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/rizhi-kote/Student/sem-5/OS/lab4

# Include any dependencies generated for this target.
include server/CMakeFiles/server.dir/depend.make

# Include the progress variables for this target.
include server/CMakeFiles/server.dir/progress.make

# Include the compile flags for this target's objects.
include server/CMakeFiles/server.dir/flags.make

server/CMakeFiles/server.dir/main.cpp.o: server/CMakeFiles/server.dir/flags.make
server/CMakeFiles/server.dir/main.cpp.o: server/main.cpp
	$(CMAKE_COMMAND) -E cmake_progress_report /home/rizhi-kote/Student/sem-5/OS/lab4/CMakeFiles $(CMAKE_PROGRESS_1)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Building CXX object server/CMakeFiles/server.dir/main.cpp.o"
	cd /home/rizhi-kote/Student/sem-5/OS/lab4/server && /usr/bin/c++   $(CXX_DEFINES) $(CXX_FLAGS) -o CMakeFiles/server.dir/main.cpp.o -c /home/rizhi-kote/Student/sem-5/OS/lab4/server/main.cpp

server/CMakeFiles/server.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/server.dir/main.cpp.i"
	cd /home/rizhi-kote/Student/sem-5/OS/lab4/server && /usr/bin/c++  $(CXX_DEFINES) $(CXX_FLAGS) -E /home/rizhi-kote/Student/sem-5/OS/lab4/server/main.cpp > CMakeFiles/server.dir/main.cpp.i

server/CMakeFiles/server.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/server.dir/main.cpp.s"
	cd /home/rizhi-kote/Student/sem-5/OS/lab4/server && /usr/bin/c++  $(CXX_DEFINES) $(CXX_FLAGS) -S /home/rizhi-kote/Student/sem-5/OS/lab4/server/main.cpp -o CMakeFiles/server.dir/main.cpp.s

server/CMakeFiles/server.dir/main.cpp.o.requires:
.PHONY : server/CMakeFiles/server.dir/main.cpp.o.requires

server/CMakeFiles/server.dir/main.cpp.o.provides: server/CMakeFiles/server.dir/main.cpp.o.requires
	$(MAKE) -f server/CMakeFiles/server.dir/build.make server/CMakeFiles/server.dir/main.cpp.o.provides.build
.PHONY : server/CMakeFiles/server.dir/main.cpp.o.provides

server/CMakeFiles/server.dir/main.cpp.o.provides.build: server/CMakeFiles/server.dir/main.cpp.o

server/CMakeFiles/server.dir/Server.cpp.o: server/CMakeFiles/server.dir/flags.make
server/CMakeFiles/server.dir/Server.cpp.o: server/Server.cpp
	$(CMAKE_COMMAND) -E cmake_progress_report /home/rizhi-kote/Student/sem-5/OS/lab4/CMakeFiles $(CMAKE_PROGRESS_2)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Building CXX object server/CMakeFiles/server.dir/Server.cpp.o"
	cd /home/rizhi-kote/Student/sem-5/OS/lab4/server && /usr/bin/c++   $(CXX_DEFINES) $(CXX_FLAGS) -o CMakeFiles/server.dir/Server.cpp.o -c /home/rizhi-kote/Student/sem-5/OS/lab4/server/Server.cpp

server/CMakeFiles/server.dir/Server.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/server.dir/Server.cpp.i"
	cd /home/rizhi-kote/Student/sem-5/OS/lab4/server && /usr/bin/c++  $(CXX_DEFINES) $(CXX_FLAGS) -E /home/rizhi-kote/Student/sem-5/OS/lab4/server/Server.cpp > CMakeFiles/server.dir/Server.cpp.i

server/CMakeFiles/server.dir/Server.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/server.dir/Server.cpp.s"
	cd /home/rizhi-kote/Student/sem-5/OS/lab4/server && /usr/bin/c++  $(CXX_DEFINES) $(CXX_FLAGS) -S /home/rizhi-kote/Student/sem-5/OS/lab4/server/Server.cpp -o CMakeFiles/server.dir/Server.cpp.s

server/CMakeFiles/server.dir/Server.cpp.o.requires:
.PHONY : server/CMakeFiles/server.dir/Server.cpp.o.requires

server/CMakeFiles/server.dir/Server.cpp.o.provides: server/CMakeFiles/server.dir/Server.cpp.o.requires
	$(MAKE) -f server/CMakeFiles/server.dir/build.make server/CMakeFiles/server.dir/Server.cpp.o.provides.build
.PHONY : server/CMakeFiles/server.dir/Server.cpp.o.provides

server/CMakeFiles/server.dir/Server.cpp.o.provides.build: server/CMakeFiles/server.dir/Server.cpp.o

server/CMakeFiles/server.dir/Buffer.cpp.o: server/CMakeFiles/server.dir/flags.make
server/CMakeFiles/server.dir/Buffer.cpp.o: server/Buffer.cpp
	$(CMAKE_COMMAND) -E cmake_progress_report /home/rizhi-kote/Student/sem-5/OS/lab4/CMakeFiles $(CMAKE_PROGRESS_3)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Building CXX object server/CMakeFiles/server.dir/Buffer.cpp.o"
	cd /home/rizhi-kote/Student/sem-5/OS/lab4/server && /usr/bin/c++   $(CXX_DEFINES) $(CXX_FLAGS) -o CMakeFiles/server.dir/Buffer.cpp.o -c /home/rizhi-kote/Student/sem-5/OS/lab4/server/Buffer.cpp

server/CMakeFiles/server.dir/Buffer.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/server.dir/Buffer.cpp.i"
	cd /home/rizhi-kote/Student/sem-5/OS/lab4/server && /usr/bin/c++  $(CXX_DEFINES) $(CXX_FLAGS) -E /home/rizhi-kote/Student/sem-5/OS/lab4/server/Buffer.cpp > CMakeFiles/server.dir/Buffer.cpp.i

server/CMakeFiles/server.dir/Buffer.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/server.dir/Buffer.cpp.s"
	cd /home/rizhi-kote/Student/sem-5/OS/lab4/server && /usr/bin/c++  $(CXX_DEFINES) $(CXX_FLAGS) -S /home/rizhi-kote/Student/sem-5/OS/lab4/server/Buffer.cpp -o CMakeFiles/server.dir/Buffer.cpp.s

server/CMakeFiles/server.dir/Buffer.cpp.o.requires:
.PHONY : server/CMakeFiles/server.dir/Buffer.cpp.o.requires

server/CMakeFiles/server.dir/Buffer.cpp.o.provides: server/CMakeFiles/server.dir/Buffer.cpp.o.requires
	$(MAKE) -f server/CMakeFiles/server.dir/build.make server/CMakeFiles/server.dir/Buffer.cpp.o.provides.build
.PHONY : server/CMakeFiles/server.dir/Buffer.cpp.o.provides

server/CMakeFiles/server.dir/Buffer.cpp.o.provides.build: server/CMakeFiles/server.dir/Buffer.cpp.o

server/CMakeFiles/server.dir/Configs.cpp.o: server/CMakeFiles/server.dir/flags.make
server/CMakeFiles/server.dir/Configs.cpp.o: server/Configs.cpp
	$(CMAKE_COMMAND) -E cmake_progress_report /home/rizhi-kote/Student/sem-5/OS/lab4/CMakeFiles $(CMAKE_PROGRESS_4)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Building CXX object server/CMakeFiles/server.dir/Configs.cpp.o"
	cd /home/rizhi-kote/Student/sem-5/OS/lab4/server && /usr/bin/c++   $(CXX_DEFINES) $(CXX_FLAGS) -o CMakeFiles/server.dir/Configs.cpp.o -c /home/rizhi-kote/Student/sem-5/OS/lab4/server/Configs.cpp

server/CMakeFiles/server.dir/Configs.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/server.dir/Configs.cpp.i"
	cd /home/rizhi-kote/Student/sem-5/OS/lab4/server && /usr/bin/c++  $(CXX_DEFINES) $(CXX_FLAGS) -E /home/rizhi-kote/Student/sem-5/OS/lab4/server/Configs.cpp > CMakeFiles/server.dir/Configs.cpp.i

server/CMakeFiles/server.dir/Configs.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/server.dir/Configs.cpp.s"
	cd /home/rizhi-kote/Student/sem-5/OS/lab4/server && /usr/bin/c++  $(CXX_DEFINES) $(CXX_FLAGS) -S /home/rizhi-kote/Student/sem-5/OS/lab4/server/Configs.cpp -o CMakeFiles/server.dir/Configs.cpp.s

server/CMakeFiles/server.dir/Configs.cpp.o.requires:
.PHONY : server/CMakeFiles/server.dir/Configs.cpp.o.requires

server/CMakeFiles/server.dir/Configs.cpp.o.provides: server/CMakeFiles/server.dir/Configs.cpp.o.requires
	$(MAKE) -f server/CMakeFiles/server.dir/build.make server/CMakeFiles/server.dir/Configs.cpp.o.provides.build
.PHONY : server/CMakeFiles/server.dir/Configs.cpp.o.provides

server/CMakeFiles/server.dir/Configs.cpp.o.provides.build: server/CMakeFiles/server.dir/Configs.cpp.o

# Object files for target server
server_OBJECTS = \
"CMakeFiles/server.dir/main.cpp.o" \
"CMakeFiles/server.dir/Server.cpp.o" \
"CMakeFiles/server.dir/Buffer.cpp.o" \
"CMakeFiles/server.dir/Configs.cpp.o"

# External object files for target server
server_EXTERNAL_OBJECTS =

server/server: server/CMakeFiles/server.dir/main.cpp.o
server/server: server/CMakeFiles/server.dir/Server.cpp.o
server/server: server/CMakeFiles/server.dir/Buffer.cpp.o
server/server: server/CMakeFiles/server.dir/Configs.cpp.o
server/server: server/CMakeFiles/server.dir/build.make
server/server: server/CMakeFiles/server.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --red --bold "Linking CXX executable server"
	cd /home/rizhi-kote/Student/sem-5/OS/lab4/server && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/server.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
server/CMakeFiles/server.dir/build: server/server
.PHONY : server/CMakeFiles/server.dir/build

server/CMakeFiles/server.dir/requires: server/CMakeFiles/server.dir/main.cpp.o.requires
server/CMakeFiles/server.dir/requires: server/CMakeFiles/server.dir/Server.cpp.o.requires
server/CMakeFiles/server.dir/requires: server/CMakeFiles/server.dir/Buffer.cpp.o.requires
server/CMakeFiles/server.dir/requires: server/CMakeFiles/server.dir/Configs.cpp.o.requires
.PHONY : server/CMakeFiles/server.dir/requires

server/CMakeFiles/server.dir/clean:
	cd /home/rizhi-kote/Student/sem-5/OS/lab4/server && $(CMAKE_COMMAND) -P CMakeFiles/server.dir/cmake_clean.cmake
.PHONY : server/CMakeFiles/server.dir/clean

server/CMakeFiles/server.dir/depend:
	cd /home/rizhi-kote/Student/sem-5/OS/lab4 && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/rizhi-kote/Student/sem-5/OS/lab4 /home/rizhi-kote/Student/sem-5/OS/lab4/server /home/rizhi-kote/Student/sem-5/OS/lab4 /home/rizhi-kote/Student/sem-5/OS/lab4/server /home/rizhi-kote/Student/sem-5/OS/lab4/server/CMakeFiles/server.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : server/CMakeFiles/server.dir/depend

