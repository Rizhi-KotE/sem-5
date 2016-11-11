package teaching;

import Jama.Matrix;
import lombok.Data;

@Data
public class NormalizeNeuronsTeacher extends NeuronsTeacher {


    @Override
    public void correction(Matrix Xi, Matrix yi, Matrix deltaXi) {
        super.correction(Xi, yi, deltaXi);
        normalize(getNetwork().getW());
        normalize(getNetwork().getWH());
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
        normalize(getNetwork().getW());
        normalize(getNetwork().getWH());
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
