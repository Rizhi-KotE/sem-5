package network;

import Jama.Matrix;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NormalizeNeuronsTeacher extends NeuronsTeacher {
    private List<double[]> plot = new ArrayList<>();
    double epochNumber;

    long timeStart;

    @Override
    protected void beforeNextEpoch(double e) {
        super.beforeNextEpoch(e);
        timeStart = System.currentTimeMillis();
    }

    @Override
    protected void afterEpoch(double e) {
        super.afterEpoch(e);
        plot.add(new double[]{System.currentTimeMillis() - timeStart, e});
        epochNumber++;
    }

    @Override
    public void correction(Matrix Xi, Matrix yi, Matrix deltaXi) {
        super.correction(Xi, yi, deltaXi);
        normalize(getNetwork().getW());
        normalize(getNetwork().getWH());

    }

    private void normalize(Matrix w) {
        double[][] matrix = w.getArray();
        for (int i = 0; i < w.getRowDimension(); i++) {
            double abs = 0;
            for (int j = 0; j < w.getColumnDimension(); j++) {
                abs += matrix[i][j] * matrix[i][j];
            }
            abs = Math.sqrt(abs);
            for (int j = 0; j < w.getColumnDimension(); j++) {
                matrix[i][j] = matrix[i][j] / abs;
            }
        }
    }

    public ResultTeaching getResultTeaching(){
        return new ResultTeaching(getBestE(), (int) epochNumber, 0, getN(), getP(), getL(), getE(), getStep());
    }
}
