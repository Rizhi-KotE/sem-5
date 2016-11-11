

public class TangensFunction implements ActivationFunction {
    @Override
    public double getValue(double x) {
        return Math.tanh(x);
    }

    @Override
    public double getDerivativeValue(double x) {
        return Math.cosh(x);
    }
}
