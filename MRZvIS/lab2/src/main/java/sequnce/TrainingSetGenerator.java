package sequnce;

import Jama.Matrix;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TrainingSetGenerator {

    private final Sequence sequence;

    public TrainingSetGenerator(Sequence sequence) {
        this.sequence = sequence;
    }

    public Stream<Pair<Matrix, Matrix>> createTrainingSet(int division) {
        double[] sequence = this.sequence.getSequence().toArray();
        List<Matrix> list = new ArrayList<>();
        for (int pairNumber = 0; pairNumber < sequence.length - division - 1 - 1; pairNumber++) {
            double[][] input = new double[1][division + 1 + 1];
            for (int i = 0; i < division + 1; i++) {
                input[0][i] = sequence[pairNumber + i];
            }
            input[0][division + 1 + 1 - 1] = input[0][division + 1 - 1];
            list.add(new Matrix(input));
        }
        return list.stream()
                .map(matrix ->
                        new Pair<Matrix, Matrix>(matrix.copy(),
                                matrix.getMatrix(0, 0, division, division)));
    }
}
