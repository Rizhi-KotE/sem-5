import Jama.Matrix;

import java.util.*;

/**
 * Created by toli444 on 13.9.16.
 */
public class NeuronsTeacher {
    private int L; //Число векторов Xi
    private int N; //Длина вектора Xi
    private int p; //Заданное пользователем значение
    private double aH;
    private double e;
    private NeuronsNetwork network;
    private Iterator<Matrix> matrixIterator;
    private final List<Matrix> listOfArrayXi;
    private Matrix bestWh;
    private Matrix bestW;
    private int failStep;
    private double bestE = Double.MAX_VALUE;
    private boolean hasProgress;

    public NeuronsTeacher(Matrix[] ArrayOfXi, int N, int p, double aH, double e) {
        listOfArrayXi = new ArrayList<>(ArrayOfXi.length);
        listOfArrayXi.addAll(Arrays.asList(ArrayOfXi));
        this.L = ArrayOfXi.length;
        this.N = N;
        this.p = p;
        this.aH = aH;
        this.e = e;
    }

    public void runTeaching() {
        double E;
        do {
            E = runEpoch();
        } while (E > e);

        System.out.println("Finished");
    }

    public double runEpoch() throws NumberFormatException {
        Collections.shuffle(listOfArrayXi);
        double E = 0;
        hasProgress = false;
        try {
            for (Matrix matrix : listOfArrayXi) {

                E += subStep(matrix);
                if (Double.isNaN(network.getW().getArray()[0][0])) {
                    throw new NumberFormatException("bad matrix");
                }
                failStep++;
            }
        } finally {
            if (bestE > E) {
                hasProgress = true;
                failStep = 0;
                bestE = E;
                bestW = network.getW();
                bestWh = network.getWH();
                System.out.println("Best E: " + bestE);
            }
            return E;
        }
    }

    public NeuronsNetwork getBestNetwork() {
        NeuronsNetwork network = new NeuronsNetwork();
        network.setW(bestW);
        network.setWH(bestWh);
        return network;
    }

    public double nextStep() {
        if (matrixIterator == null) {
            matrixIterator = listOfArrayXi.iterator();
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


        Matrix newWH = calculateNewWH(network.getWH(), Yi, deltaXi);
        Matrix newW = calculateNewW(network.getWH(), network.getW(), Xi, deltaXi);
        network.setWH(newWH);
        network.setW(newW);

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
        Matrix correction = transposedYi.times(deltaXi);
        return WH.minus(correction);
    }

    private Matrix calculateNewW(Matrix WH, Matrix W, Matrix Xi, Matrix deltaXi) {
        Matrix transposedXi = Xi.transpose();
        Matrix transposedWh = WH.transpose();
        Matrix correction = transposedXi.times(deltaXi).times(transposedWh).times(aH);
        return W.minus(correction);
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

    public int getL() {
        return L;
    }

    public void setL(int l) {
        L = l;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public double getaH() {
        return aH;
    }

    public void setaH(double aH) {
        this.aH = aH;
    }

    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
    }

    public Iterator<Matrix> getMatrixIterator() {
        return matrixIterator;
    }

    public void setMatrixIterator(Iterator<Matrix> matrixIterator) {
        this.matrixIterator = matrixIterator;
    }

    public List<Matrix> getListOfArrayXi() {
        return listOfArrayXi;
    }

    public Matrix getBestWh() {
        return bestWh;
    }

    public void setBestWh(Matrix bestWh) {
        this.bestWh = bestWh;
    }


    public boolean isHasProgress() {
        return hasProgress;
    }

    public void setHasProgress(boolean hasProgress) {
        this.hasProgress = hasProgress;
    }

    public Matrix getBestW() {
        return bestW;
    }

    public void setBestW(Matrix bestW) {
        this.bestW = bestW;
    }

    public int getFailStep() {
        return failStep;
    }

    public void setFailStep(int failStep) {
        this.failStep = failStep;
    }

    public double getBestE() {
        return bestE;
    }

    public void setBestE(double bestE) {
        this.bestE = bestE;
    }
}
