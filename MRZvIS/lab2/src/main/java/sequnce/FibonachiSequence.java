package sequnce;

import java.util.function.IntToDoubleFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class FibonachiSequence implements Sequence {

    private long previos = 1;
    private long previosPrevios = 0;

    private final int length;

    public FibonachiSequence(int length) {
        this.length = length;
    }

    public double next() {
        long result = previos + previosPrevios;
        previosPrevios = previos;
        previos = result;
        return result;
    }

    @Override
    public DoubleStream getSequence() {
        return DoubleStream.generate(this::next).limit(length);
    }
}
