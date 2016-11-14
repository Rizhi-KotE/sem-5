package teaching;

import Jama.Matrix;

import java.util.List;

public class NormalizeNeuronsTeacher extends NeuronsTeacher {

    public NormalizeNeuronsTeacher(int l, int n, int p, double step, double e, NeuronsNetwork network, List<Matrix> listOfArrayXi) {
        super(l, n, p, step, e, network, listOfArrayXi);
    }

    @Override
    public void correction(Matrix Xi, Matrix yi, Matrix deltaXi) {
        super.correction(Xi, yi, deltaXi);
        normalize(network.getW());
        normalize(network.getWH());
//        normalizeTransponce(getNetwork().getWH());
    }

    private void normalizeTransponce(Matrix w) {
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
                matrix[i][j] /= abs;
            }
        }
    }

    @Override
    protected void beforeFirstEpoch() {
        normalize(network.getW());
        normalize(network.getWH());
        super.beforeFirstEpoch();
    }


    private void normalize(Matrix w) {
        if (Double.isNaN(w.getArray()[0][0])) {
            throw new NumberFormatException("bad matrix");
        }
        double[][] matrix = w.getArray();
        for (int j = 0; j < w.getColumnDimension(); j++) {
            double abs = 0;
            for (int i = 0; i < w.getRowDimension(); i++) {
                abs += matrix[i][j] * matrix[i][j];
            }
            abs = Math.sqrt(abs);

            for (int i = 0; i < w.getRowDimension(); i++) {
                matrix[i][j] /= abs;
            }
        }
    }

}
