package sequnce;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class PowerSecuence implements Sequence {

    private final int length;

    public PowerSecuence(int length) {
        this.length = length;
    }

    @Override
    public DoubleStream getSequence() {
        return IntStream.range(0, length).mapToDouble(value -> Math.pow(0.9, value));
    }
}
