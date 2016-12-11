#!/bin/sh

cat /home/rizhi-kote/Student/sem-5/MRZvIS/lab2/src/main/resources/prediction.data |
ruby add_delta_column.rb > $1.csv
echo "set terminal svg
set output  '$1.svg'
" >> tmp &&
cat tmp plot.gnuplot | gnuplot 
rm tmp
cat /home/rizhi-kote/Student/sem-5/MRZvIS/lab2/src/main/resources/prediction.data > $1_prediction_src.data
rm /home/rizhi-kote/Student/sem-5/MRZvIS/lab2/src/main/resources/prediction.data