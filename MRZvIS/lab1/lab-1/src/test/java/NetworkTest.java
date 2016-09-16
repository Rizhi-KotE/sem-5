import Jama.Matrix;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class NetworkTest {

    NeuronsNetwork network;
    private double[][] expectedY;
    private double[][] expectedX;

    @Before
    public void onStart() {
        double[][] matrix = {{1, 2, 5}, {3, 4, 6}};
        Matrix W = new Matrix(matrix);
        Matrix WH = W.transpose();
        network = new NeuronsNetwork();
        network.setW(W);
        network.setWH(WH);

        expectedX = new double[][]{{1, 2}};
        expectedY = new double[][]{{7, 10, 17}};
    }

    @Test
    public void zipTest() {
        Matrix x = new Matrix(expectedX);
        Matrix outYi = network.zip(x);
        assertArrayEquals(expectedY, outYi.getArray());
    }

    @Test
    public void unzipTest() {
        Matrix x = new Matrix(expectedX);
        Matrix outYi = network.zip(x);
        assertArrayEquals(expectedY, outYi.getArray());
    }
}
