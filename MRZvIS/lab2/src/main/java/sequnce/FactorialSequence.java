package sequnce;

import java.util.stream.DoubleStream;

public class FactorialSequence implements Sequence {
    private final int length;
    double currentArgument = 0;

    double currentValue = 1;

    FactorialSequence(int length){
        this.length = length;
    }

    public double getNext() {
        return currentValue = currentValue * ++currentArgument;
    }

    @Override
    public DoubleStream getSequence() {
        return DoubleStream.generate(this::getNext).limit(length);
    }
}
