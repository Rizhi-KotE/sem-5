package teaching;

import Jama.Matrix;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by toli444 on 16.9.16.
 */
@JsonDeserialize(using = NetworkDeserializer.class)
@JsonSerialize(using = NetworkSerializer.class)
public class NeuronsNetwork {

    private final int n;
    private final int p;

    private Matrix W;
    private Matrix WH;

    public NeuronsNetwork(int n, int p) {
        this.n = n;
        this.p = p;
        Matrix W = Matrix.random(n, p);
        W = W.times(2.).minus(new Matrix(n, p, 1.)).times(0.25);
        Matrix WH = W.transpose();
        this.W = W;
        this.WH = WH;
    }

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

    public Matrix pack(Matrix Xi) {
        return Xi.times(W);
    }

    public Matrix extract(Matrix Yi) {
//        Matrix WH = W.transpose();
        return Yi.times(WH);
    }

}
