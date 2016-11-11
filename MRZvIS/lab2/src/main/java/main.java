import Jama.Matrix;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;


public class main {

    public static List<Pair<Matrix, Matrix>> createTrainingSet(int length, int division) {
        FactorialSequence factorialSequence = new FactorialSequence();
        double[] sequence = DoubleStream.generate(factorialSequence::getNext).limit(length + division + 1).toArray();
        List<Pair<Matrix, Matrix>> list = new ArrayList<>();
        for (int pairNumber = 0; pairNumber < length; pairNumber++) {
            double[][] input = new double[1][division];
            for (int i = 0; i < division; i++) {
                input[0][i] = sequence[pairNumber + i];
            }
            Pair<Matrix, Matrix> pair = new Pair<>(new Matrix(input), new Matrix(new double[][]{{sequence[pairNumber + division]}}));
            list.add(pair);
        }
        return list;
    }

    public static void main(String[] args) {
        List<Pair<Matrix, Matrix>> check = createTrainingSet(7, 3).stream().map(pair -> {
            Matrix matrix = new Matrix(1, 4);
            matrix.setMatrix(0, 0, 0, 3 - 1, pair.getKey());
            return new Pair<>(matrix, pair.getValue());
        }).collect(Collectors.toList());
        JordansNetworkWithTangens network = new JordansNetworkWithTangens(3);
        double error = 100001;
        while (error > 10000) {
            List<Pair<Matrix, Matrix>> trainingSet = createTrainingSet(5, 3)
                    .stream().map(pair -> {
                        Matrix matrix = new Matrix(1, 4);
                        matrix.setMatrix(0, 0, 0, 3 - 1, pair.getKey());
                        return new Pair<>(matrix, pair.getValue());
                    }).collect(Collectors.toList());
            for (Pair<Matrix, Matrix> pair : trainingSet) {
                Matrix matrix = network.straightPropagation(pair.getKey());
                Matrix delta = matrix.minus(pair.getValue());
                network.backPropagate(delta, 0.001);
            }
            error = 0;
            for (Pair<Matrix, Matrix> pair : trainingSet) {
                error += Math.pow(network.straightPropagation(pair.getKey()).get(0, 0) - pair.getValue().get(0, 0), 2);
            }
            System.out.println(error);
        }
        for (int i = 5; i < 7; i++) {
            Pair<Matrix, Matrix> pair = check.get(i);
            Matrix result = network.straightPropagation(pair.getKey());
            System.out.println("Expected " +  pair.getValue().get(0, 0) + " ,result is " + result.get(0, 0));
        }
    }
}
