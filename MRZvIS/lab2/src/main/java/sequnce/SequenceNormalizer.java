package sequnce;

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class SequenceNormalizer {
    protected final DoubleStream stream;
    private double max;
    private double min;

    public SequenceNormalizer(DoubleStream stream) {
        this.stream = stream;
    }

    public DoubleStream normalize() {
        double[] doubles = stream.map(value -> {
            max = Math.max(Math.abs(max), value);
            return value;
        }).toArray();
        max *= 1.1;
        return Arrays.stream(doubles).map(operand -> operand / max);
    }

    public double denormilize(double value) {
        return value * max;
    }
}
