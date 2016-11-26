import Jama.Matrix;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

public class HemmingNetwork {
    private final int inputSize;
    private final int layersSize;

    private final Matrix inputWeights;

    private final Matrix workingWeights;
    private final double t;
    private int iterations;

    public HemmingNetwork(int inputSize, List<int[]> images) throws IllegalAccessException {
        this.inputSize = inputSize;
        layersSize = images.size();
        t = (double) inputSize / 2;
        inputWeights = initInputWeights(images);
        workingWeights = initWorkingsWeights();
    }

    private Matrix initWorkingsWeights() {
        Matrix matrix = new Matrix(layersSize, layersSize, -1. / layersSize);
        for (int i = 0; i < layersSize; i++) {
            matrix.set(i, i, 1.);
        }
        return matrix;
    }

    private Matrix initInputWeights(List<int[]> images) throws IllegalAccessException {
        Matrix matrix = new Matrix(inputSize, layersSize);
        for (int k = 0; k < images.size(); k++) {
            int[] image = images.get(k);
            if (image.length != inputSize) {
                throw new IllegalArgumentException("images should be " + inputSize + " length");
            }
            for (int i = 0; i < image.length; i++) {
                if (image[i] != -1 && image[i] != 1) {
                    throw new IllegalAccessException("images must containt only {-1, 1}");
                }
                matrix.set(i, k, image[i] / 2.);
            }
        }
        return matrix;
    }

    public int[] run(int[] image) throws IllegalAccessException {
        if (image.length != inputSize) {
            throw new IllegalArgumentException("images should be " + inputSize + " length");
        }
        Matrix matrix = new Matrix(1, inputSize);
        for (int i = 0; i < image.length; i++) {
            if (image[i] != -1 && image[i] != 1) {
                throw new IllegalAccessException("images must containt only {-1, 1}");
            }
            matrix.set(0, i, image[i]);
        }

        Matrix result = startWork(matrix);
        return Arrays
                .stream(result.getArray()[0])
                .mapToInt(value -> value > 0 ? 1 : 0)
                .toArray();
    }

    private Matrix activationFunction(Matrix signals) {
        double[][] array = signals.getArray();
        for (int i = 0; i < layersSize; i++) {
            if (array[0][i] < 0) {
                array[0][i] = 0;
            }
        }
        return signals;
    }

    private Matrix startWork(Matrix matrix) {
        iterations = 0;
        Matrix oldAxons = matrix.times(inputWeights);
        oldAxons.plusEquals(new Matrix(1, layersSize, t));
        oldAxons = activationFunction(oldAxons);
        for (Matrix newAxons = null; newAxons == null || hemmingDistance(newAxons, oldAxons) != 0; ) {
            iterations++;
            if(newAxons != null) oldAxons = newAxons;
            newAxons = oldAxons.copy();
            Matrix correction = newAxons.times(workingWeights);
            newAxons = newAxons.plusEquals(correction);
            newAxons = activationFunction(newAxons);
        }
        return oldAxons;
    }

    public int getIterations() {
        return iterations;
    }

    private int hemmingDistance(Matrix newAxons, Matrix oldAxons) {
        int distance = 0;
        for (int i = 0; i < newAxons.getColumnDimension(); i++) {
            if (abs(newAxons.get(0, i) - oldAxons.get(0, i)) < 0.000001) distance++;
        }
        return distance;
    }
}
