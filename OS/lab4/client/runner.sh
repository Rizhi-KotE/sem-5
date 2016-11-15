#!/bin/bash

for ((a=1; a <= 10 ; a++))
do
	./client $1 $2 "message from client $a" &
done