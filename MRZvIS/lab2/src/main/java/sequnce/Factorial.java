package sequnce;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Factorial implements Sequence {

    private double previos = 1;

    public Factorial(int length) {
        this.length = length;
    }

    private final int length;

    @Override
    public DoubleStream getSequence() {
        return IntStream.range(0, length).mapToDouble(value -> previos *= value == 0. ? 1. : value);
    }
}
