import sequnce.Sequence;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class LinearSequnce implements Sequence {

    private final int length;

    public LinearSequnce(int length) {
        this.length = length;
    }

    @Override
    public DoubleStream getSequence() {
        return IntStream.range(0, length).mapToDouble(value -> value);
    }
}
