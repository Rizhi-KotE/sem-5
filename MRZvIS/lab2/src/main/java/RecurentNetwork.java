import Jama.Matrix;

public interface RecurentNetwork {
    void freezeContext();

    void unfreezeContext();

    Matrix straightPropagation(Matrix vector);

    void backPropagate(Matrix outputDifference, double teachingStep);

    void resetContext();
}
