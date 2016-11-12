package sequnce;

import java.util.function.IntToDoubleFunction;

public class Fibonachi implements IntToDoubleFunction {

    private long previos;

    @Override
    public double applyAsDouble(int value) {
        double out = previos++ + value;
        return out / Double.MAX_VALUE;
    }
}
