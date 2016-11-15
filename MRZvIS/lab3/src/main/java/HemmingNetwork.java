import Jama.Matrix;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class HemmingNetwork {
    private final int inputSize;
    private final int layersSize;

    private final Matrix inputWeights;

    private final Matrix workingWeights;

    public HemmingNetwork(int inputSize, List<int[]> images) throws IllegalAccessException {
        this.inputSize = inputSize;
        layersSize = images.size();
        inputWeights = initInputWeights(images);
        workingWeights = initWorkingsWeights();
    }

    private Matrix initWorkingsWeights() {
        Matrix matrix = Matrix.random(layersSize, layersSize)
                .timesEquals(1 / layersSize);
        for (int i = 0; i < layersSize; i++) {
            matrix.set(i, i, 1.);
        }
        return matrix;
    }

    private Matrix initInputWeights(List<int[]> images) throws IllegalAccessException {
        Matrix matrix = new Matrix(inputSize + 1, layersSize);
        for (int k = 0; k < images.size(); k++) {
            int[] image = images.get(k);
            if (image.length != inputSize) {
                throw new IllegalArgumentException("images should be " + inputSize + " length");
            }
            for (int i = 0; i < image.length; i++) {
                if (image[i] != -1 || image[i] != 1) {
                    throw new IllegalAccessException("images must containt only {-1, 1}");
                }
                matrix.set(i, k, image[k] / 2.);
            }
            matrix.set(inputSize, k, (double) inputSize / 2.);
        }
        return matrix;
    }

    public int[] run(int[] image) throws IllegalAccessException {
        if (image.length != inputSize) {
            throw new IllegalArgumentException("images should be " + inputSize + " length");
        }
        Matrix matrix = new Matrix(1, inputSize + 1);
        for (int i = 0; i < image.length; i++) {
            if (image[i] != -1 || image[i] != 1) {
                throw new IllegalAccessException("images must containt only {-1, 1}");
            }
            matrix.set(0, i, image[i]);
        }
        Matrix result = startWork(matrix);
        return Arrays
                .stream(result.getArray()[0])
                .mapToInt(value -> (int)value)
                .toArray();
    }

    private Matrix startWork(Matrix matrix) {
        Matrix oldAxons = matrix.times(inputWeights);
        for (Matrix newAxons = oldAxons; axonsEquals(newAxons, oldAxons); oldAxons = newAxons) {
            newAxons = activationFunction(newAxons
                    .times(workingWeights));
        }
        return oldAxons;
    }

    private Matrix activationFunction(Matrix signals) {
        double[][] array = signals.getArray();
        for (int i = 0; i < layersSize; i++) {
            array[0][i] = array[0][i] <= 0 ? 0 : array[0][i];
        }
        return signals;
    }

    private boolean axonsEquals(Matrix f, Matrix s) {
        if (f.getColumnDimension() != s.getColumnDimension()) return false;
        if (f.getColumnDimension() != 1) return false;
        if (f.getRowDimension() != s.getRowDimension()) return false;
        for (int i = 0; i < f.getRowDimension(); i++) {
            if (f.get(0, i) != s.get(0, i)) return false;
        }
        return true;
    }
}
