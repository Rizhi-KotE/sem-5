import java.util.stream.DoubleStream;

public class ScalingSequence implements Sequence {

    private final Sequence sequence;

    ScalingSequence(Sequence sequence){
        this.sequence = sequence;
    }

    @Override
    public DoubleStream getSequence(int length) {

    }
}
