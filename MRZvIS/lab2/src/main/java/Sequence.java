import java.util.stream.DoubleStream;

public interface Sequence {
    DoubleStream getSequence(int length);
}
