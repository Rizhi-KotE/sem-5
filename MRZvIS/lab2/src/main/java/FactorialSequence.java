import java.util.stream.DoubleStream;

public class FactorialSequence implements Sequence {
    double currentArgument = 0;

    double currentValue = 1;

    public double getNext() {
        return currentValue = currentValue * ++currentArgument;
    }

    @Override
    public DoubleStream getSequence(int length) {
        return null;
    }
}
