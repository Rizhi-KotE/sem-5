#!/bin/sh

echo "1 2" | ./run_prediction.sh && ./executor.sh 'pow2' &&
echo "1 3" | ./run_prediction.sh && ./executor.sh 'pow3' &&
echo "1 4" | ./run_prediction.sh && ./executor.sh 'pow4' &&
echo "2 2" | ./run_prediction.sh && ./executor.sh 'cos2' &&
echo "2 3" | ./run_prediction.sh && ./executor.sh 'cos3' &&
echo "2 4" | ./run_prediction.sh && ./executor.sh 'cos4' &&
echo "2 5" | ./run_prediction.sh && ./executor.sh 'cos5' &&
echo "3 2" | ./run_prediction.sh && ./executor.sh 'lin2' &&
echo "3 3" | ./run_prediction.sh && ./executor.sh 'lin3' &&
echo "3 4" | ./run_prediction.sh && ./executor.sh 'lin4' &&
echo "3 5" | ./run_prediction.sh && ./executor.sh 'lin5' &&
echo "4 1" | ./run_prediction.sh && ./executor.sh 'fib1' &&
echo "4 2" | ./run_prediction.sh && ./executor.sh 'fib2' &&
echo "4 3" | ./run_prediction.sh && ./executor.sh 'fib3' &&
echo "4 4" | ./run_prediction.sh && ./executor.sh 'fib4'