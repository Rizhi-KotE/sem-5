package sequnce;

import java.util.function.IntToDoubleFunction;

public class Factorial implements IntToDoubleFunction {

    public static final int MAX_FACTORIAL_ARGUMENT = 170;
    private static final double MAX_FACTORIAL_VALUE = 1.0567000022288868e302;
    private double previos = 1;

    @Override
    public double applyAsDouble(int value) {
        if (value < MAX_FACTORIAL_ARGUMENT) {
            previos *= value == 0. ? 1. : value;
        }
        return previos;
    }
}
