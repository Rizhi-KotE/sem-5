import Jama.Matrix;

/**
 * Created by toli444 on 16.9.16.
 */
public class NeuronsNetwork {

    private Matrix W;
    private Matrix WH;

    public Matrix getWH() {
        return WH;
    }

    public void setWH(Matrix WH) {
        this.WH = WH;
    }

    public Matrix getW() {

        return W;
    }

    public void setW(Matrix w) {
        W = w;
    }

    public Matrix zip(Matrix Xi) {
        return Xi.times(W);
    }

    public Matrix unzip(Matrix Yi) {
        Matrix WH = W.transpose();
        return Yi.times(WH);
    }

}
