package sequnce;

import Jama.Matrix;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class TrainingSetGenerator {

    private final DoubleStream stream;

    public TrainingSetGenerator(DoubleStream stream) {
        this.stream = stream;
    }

    public Stream<Pair<Matrix, Matrix>> createTrainingSet(int division) {
        double[] sequence = stream.toArray();
        List<Matrix> list = new ArrayList<>();
        for (int pairNumber = 0; pairNumber < sequence.length - division - 1 - 1; pairNumber++) {
            double[][] input = new double[1][division + 1];
            for (int i = 0; i < division + 1; i++) {
                input[0][i] = sequence[pairNumber + i];
            }
            list.add(new Matrix(input));
        }
        return list.stream()
                .map(matrix ->
                        new Pair<Matrix, Matrix>(matrix.copy(),
                                matrix.getMatrix(0, 0, division, division)));
    }
}
