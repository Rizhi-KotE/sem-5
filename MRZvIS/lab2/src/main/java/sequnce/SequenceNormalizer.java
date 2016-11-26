package sequnce;

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class SequenceNormalizer implements Sequence {
    protected final Sequence sequence;
    private double max;
    private double min;

    public SequenceNormalizer(Sequence sequence) {
        this.sequence = sequence;
    }

    public double restore(double value) {
        return (min + (value + 1) * (max - min) / 2);
    }

    public double normalize(double value) {
        return (2 * (value - min) / (max - min) - 1);
    }

    @Override
    public DoubleStream getSequence() {
        double[] doubles = sequence.getSequence().map(value -> {
            max = Math.max(max, value);
            min = Math.min(min, value);
            return value;
        }).toArray();
        return Arrays.stream(doubles).map(this::normalize);
    }
}
