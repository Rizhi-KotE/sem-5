import Jama.Matrix;

import java.util.Arrays;
import java.util.List;

public class HemmingNetwork {
    private final int inputSize;
    private final int layersSize;

    private final Matrix inputWeights;

    private final Matrix workingWeights;
    private final double t;

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

    private Matrix startWork(Matrix matrix) {
        Matrix oldAxons = matrix.times(inputWeights);
        oldAxons.plusEquals(new Matrix(1, layersSize, t));
        oldAxons = activationFunction(oldAxons);
        for (Matrix newAxons = null; newAxons == null || calcEnergy(newAxons, oldAxons) > 0.1;) {
            if(newAxons != null) oldAxons = newAxons;
            newAxons = oldAxons.copy();
            Matrix correction = newAxons.times(workingWeights);
            newAxons = newAxons.plusEquals(correction);
            newAxons = activationFunction(newAxons);
        }
        return oldAxons;
    }

    private double calcEnergy(Matrix newAxons, Matrix oldAxons) {
        double energy = 0;
        for (int i = 0; i < newAxons.getColumnDimension(); i++) {
            energy += Math.pow(newAxons.get(0, i) - oldAxons.get(0, i), 2);
        }
        return Math.sqrt(energy);
    }

    private boolean endWork(Matrix newAxons) {
        double[] doubles = newAxons.getArray()[0];
        double lastEquals = 0;
        for (int i = 0; i < doubles.length; i++) {
            if (doubles[i] <= 0) continue;
            if (lastEquals == 0) lastEquals = doubles[i];
            if (lastEquals != doubles[i]) return false;
        }
        return true;
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

    private boolean axonsEquals(Matrix f, Matrix s) {
        if (f == null || s == null) return false;
        if (f.getRowDimension() != s.getRowDimension()) return false;
        if (f.getRowDimension() != 1) return false;
        if (f.getColumnDimension() != s.getColumnDimension()) return false;
        for (int i = 0; i < f.getColumnDimension(); i++) {
            if (f.get(0, i) != s.get(0, i)) return false;
        }
        return true;
    }
}
