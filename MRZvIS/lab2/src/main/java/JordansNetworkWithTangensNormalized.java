import Jama.Matrix;

public class JordansNetworkWithTangensNormalized extends JordansNetworkWithTangens {
    public JordansNetworkWithTangensNormalized(int inputSize) {
        super(inputSize);
    }

    @Override
    public void backPropagate(Matrix outputDifference, double teachingStep) {
        super.backPropagate(outputDifference, teachingStep);
        firstW = normalizeEquals(firstW);
        secondW = normalizeEquals(secondW);
    }

    private Matrix normalizeEquals(Matrix w) {
        if (Double.isNaN(w.getArray()[0][0])) {
            throw new NumberFormatException("bad matrix");
        }
        double[][] matrix = w.getArray();
        for (int i = 0; i < w.getRowDimension(); i++) {
            double abs = 0;
            for (int j = 0; j < w.getColumnDimension(); j++) {
                abs += matrix[i][j] * matrix[i][j];
            }
            abs = Math.sqrt(abs);

            for (int j = 0; j < w.getColumnDimension(); j++) {
                matrix[i][j] /= abs == 0 ? Double.MIN_NORMAL : abs;
            }
        }
        return w;
    }
}
