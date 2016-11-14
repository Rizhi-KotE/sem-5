package sequnce;

import java.util.function.IntToDoubleFunction;

public class Fibonachi implements IntToDoubleFunction {

    private long previos = 1;
    private long previosPrevios = 0;

    @Override
    public double applyAsDouble(int value) {
        long result = previos + previosPrevios;
        previosPrevios = previos;
        previos = result;
        return result;
    }
}
