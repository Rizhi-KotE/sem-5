import Jama.Matrix;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by toli444 on 13.9.16.
 */
public class NeuronsTeacher {
    private Matrix[] arrayOfXi;
    private int L; //Число векторов Xi
    private int N; //Длина вектора Xi
    private int p; //Заданное пользователем значение
    private double aH;
    private double e;
    private NeuronsNetwork network;
    private Iterator<Matrix> matrixIterator;


    public NeuronsTeacher(Matrix[] ArrayOfXi, int N, int p, double aH, double e) {
        this.arrayOfXi = ArrayOfXi;
        this.L = ArrayOfXi.length;
        this.N = N;
        this.p = p;
        this.aH = aH;
        this.e = e;
    }

    public void runTeaching() {
        double E;
        do {
            E = stepOfTeaching();
        } while (E > e);

        System.out.println("Finished");
    }

    public double stepOfTeaching() {
        return nextStep();
    }

    public double nextStep() {
        if (matrixIterator == null) {
            matrixIterator = Arrays.asList(arrayOfXi).iterator();
        }
        if (matrixIterator.hasNext()) {
            return subStep(matrixIterator.next());
        } else {
            matrixIterator = null;
            return nextStep();
        }
    }

    public double subStep(Matrix Xi) {
        Matrix Yi = network.zip(Xi);
        Matrix XHi = network.unzip(Yi);
        Matrix deltaXi = calculateDeltaXi(XHi, Xi);


        network.setWH(calculateNewWH(network.getWH(), Yi, deltaXi));
        network.setW(calculateNewW(network.getWH(), network.getW(), Xi, deltaXi));

        return calculateEq(deltaXi);
    }

    private double calculateEq(Matrix deltaXi) {
        double Eq = 0;

        for (int q = 0; q < N; q++) {
            //TODO LOOK
            double deltaXqi = deltaXi.get(0, q);
            Eq += deltaXqi * deltaXqi;
        }
        return Eq;
    }

    private Matrix initializeW(int N, int p) {
        return Matrix.random(N, p);
    }

    private Matrix initializeWH(Matrix W) {
        return W.transpose();
    }

    private Matrix calculateNewWH(Matrix WH, Matrix Yi, Matrix deltaXi) {
        Matrix transposedYi = Yi.transpose();
        return WH.minus(transposedYi.times(aH).times(deltaXi));
    }

    private Matrix calculateNewW(Matrix WH, Matrix W, Matrix Xi, Matrix deltaXi) {
        Matrix transposedXi = Xi.transpose();
        Matrix transposedWh = WH.transpose();
        return W.minus(transposedXi.times(aH).times(deltaXi).times(transposedWh));
    }


    private Matrix calculateDeltaXi(Matrix XHi, Matrix Xi) {
        return XHi.minus(Xi);
    }

    public NeuronsNetwork getNetwork() {
        return network;
    }

    public void setNetwork(NeuronsNetwork network) {
        this.network = network;
    }
}
