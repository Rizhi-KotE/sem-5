package sequnce;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class CosinSequence implements Sequence {

    private final int length;

    public CosinSequence(int length) {
        this.length = length;
    }

    @Override
    public DoubleStream getSequence() {
        return IntStream.range(0, length).mapToDouble((x) -> Math.cos(0.5 * x));
    }
}
