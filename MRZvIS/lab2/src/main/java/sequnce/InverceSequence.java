package sequnce;

import java.util.stream.DoubleStream;

public class InverceSequence implements Sequence {

    private final Sequence sequence;

    public InverceSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public DoubleStream getSequence() {
        return sequence.getSequence().map(operand -> operand == 0 ? 0 : 1 / operand);
    }
}
