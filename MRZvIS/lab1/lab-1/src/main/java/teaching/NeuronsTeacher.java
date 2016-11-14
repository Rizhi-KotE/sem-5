package teaching;

import Jama.Matrix;
import dto.ResultTeaching;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toli444 on 13.9.16.
 */
@Getter
public class NeuronsTeacher {
    protected final NeuronsNetwork network;
    private final int L; //Число векторов Xi
    private final int N; //Длина вектора Xi
    private final int p; //Заданное пользователем значение
    private final double step;
    private final double e;
    private final List<Matrix> listOfArrayXi;
    private String image;

    public NeuronsTeacher(int l, int n, int p, double step, double e, NeuronsNetwork network, List<Matrix> listOfArrayXi) {
        L = l;
        N = n;
        this.p = p;
        this.step = step;
        this.e = e;
        this.network = network;
        this.listOfArrayXi = listOfArrayXi;
    }
    private Matrix bestWh;
    private Matrix bestW;
    private double bestE = Double.MAX_VALUE;

    private List<double[]> plot = new ArrayList<>();
    double epochNumber;

    long timeStart;

    public void runTeaching() throws NumberFormatException {
        beforeFirstEpoch();
        double E = e + 1;
        do {
            beforeNextEpoch(E);
            teachEpoch();
            E = checkError();
            afterEpoch(E);
        } while (E > e);

        System.out.println("Finished");
    }

    protected void beforeFirstEpoch() {
        System.out.print("Expected error: " + e);
        bestE = checkError();
        System.out.println("First error: " + bestE);
    }

    protected void beforeNextEpoch(double e) {

    }

    private double checkError() {
        double e = 0;
        for (Matrix xi : listOfArrayXi) {
            Matrix Yi = network.pack(xi);
            Matrix XHi = network.extract(Yi);
            Matrix deltaXi = calculateDeltaXi(XHi, xi);

            e += deltaXi.times(deltaXi.transpose()).get(0, 0);
        }
        return e;
    }

    public void teachEpoch() throws NumberFormatException {
        //Collections.shuffle(listOfArrayXi);
        int times = 0;
        try {
            for (Matrix matrix : listOfArrayXi) {

                subStep(matrix);
                times++;
                if (Double.isNaN(network.getW().getArray()[0][0])) {
                    throw new NumberFormatException("bad matrix");
                }
            }
        } catch (Exception e) {
            System.out.println(times);
            network.setW(bestW);
            network.setWH(bestWh);
            throw e;
        } finally {

        }
    }

    protected void afterEpoch(double e) {
        System.out.println("Current E: " + e);
        if (e < bestE) {
            bestE = e;
            bestW = network.getW();
            bestWh = network.getWH();
            System.out.println("Best E: " + bestE);
        }
        plot.add(new double[]{epochNumber, e});
        epochNumber++;
    }

    public NeuronsNetwork getBestNetwork() {
        NeuronsNetwork network = new NeuronsNetwork(N, p);
        network.setW(bestW);
        network.setWH(bestWh);
        return network;
    }

    public void subStep(Matrix Xi) {
        Matrix Yi = network.pack(Xi);
        Matrix XHi = network.extract(Yi);
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
        Matrix correction = transposedYi.times(deltaXi).times(step);
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


    public ResultTeaching getResultTeaching() {
        ResultTeaching result = new ResultTeaching(bestE, (int) epochNumber, 0, N, p, listOfArrayXi.size(), e, step, image);
        return result;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
