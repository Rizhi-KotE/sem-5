package network;

import Jama.Matrix;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * Created by toli444 on 13.9.16.
 */
@Data
public class NeuronsTeacher {
    private int L; //Число векторов Xi
    private int N; //Длина вектора Xi
    private int p; //Заданное пользователем значение
    private double step;
    private double e;
    protected NeuronsNetwork network;
    private List<Matrix> listOfArrayXi;
    private Matrix bestWh;
    private Matrix bestW;
    private double bestE = Double.MAX_VALUE;

    public void runTeaching() throws NumberFormatException{
        double E = e + 1;
        do {
            beforeNextEpoch(E);
            teachEpoch();
            E = checkError();
            afterEpoch(E);
        } while (E > e);

        System.out.println("Finished");
    }

    protected void beforeNextEpoch(double e) {
        if (bestE > e) {
            bestW = network.getW();
            bestWh = network.getWH();
            System.out.println("Best E: " + bestE);
        }
    }

    private double checkError() {
        double e = 0;
        for (Matrix xi : getListOfArrayXi()) {
            Matrix Yi = network.zip(xi);
            Matrix XHi = network.unzip(Yi);
            Matrix deltaXi = calculateDeltaXi(XHi, xi);

            e += deltaXi.times(deltaXi.transpose()).get(0, 0);
        }
        return e;
    }

    public void teachEpoch() throws NumberFormatException {
        Collections.shuffle(listOfArrayXi);
        try {
            for (Matrix matrix : listOfArrayXi) {

                subStep(matrix);
                if (Double.isNaN(network.getW().getArray()[0][0])) {
                    throw new NumberFormatException("bad matrix");
                }
            }
        } catch (Exception e) {
            network.setW(bestW);
            network.setWH(bestWh);
            throw e;
        } finally {

        }
    }

    protected void afterEpoch(double e) {
    }

    public NeuronsNetwork getBestNetwork() {
        NeuronsNetwork network = new NeuronsNetwork();
        network.setW(bestW);
        network.setWH(bestWh);
        return network;
    }

    public void subStep(Matrix Xi) {
        Matrix Yi = network.zip(Xi);
        Matrix XHi = network.unzip(Yi);
        Matrix deltaXi = calculateDeltaXi(XHi, Xi);


        correction(Xi, Yi, deltaXi);
    }

    public void correction(Matrix Xi, Matrix yi, Matrix deltaXi) {
        Matrix newWH = calculateNewWH(network.getWH(), yi, deltaXi);
        Matrix newW = calculateNewW(network.getWH(), network.getW(), Xi, deltaXi);
        network.setWH(newWH);
        network.setW(newW);
    }

    private Matrix calculateNewWH(Matrix WH, Matrix Yi, Matrix deltaXi) {
        Matrix transposedYi = Yi.transpose();
        Matrix correction = transposedYi.times(deltaXi);
        return WH.minus(correction);
    }

    private Matrix calculateNewW(Matrix WH, Matrix W, Matrix Xi, Matrix deltaXi) {
        Matrix transposedXi = Xi.transpose();
        Matrix transposedWh = WH.transpose();
        Matrix correction = transposedXi.times(deltaXi).times(transposedWh).times(step);
        return W.minus(correction);
    }

    private Matrix calculateDeltaXi(Matrix XHi, Matrix Xi) {
        return XHi.minus(Xi);
    }
}
