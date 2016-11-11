import Jama.Matrix;
import teaching.NeuronsNetwork;
import teaching.NormalizeNeuronsTeacher;
import org.junit.Before;
import org.junit.Test;

public class TestNormalize {

    private Matrix W;
    private Matrix WH;
    private NormalizeNeuronsTeacher teacher;

    @Before
    public void onStart(){
        double[][] wMatrix = new double[][]{{3,6},{4,8},{5,10}};
        W = new Matrix(wMatrix);
        double[][] whMatrix = new double[][]{{3,4,5},{6,8,10}};
        WH = new Matrix(whMatrix);
        teacher = new NormalizeNeuronsTeacher();
        NeuronsNetwork network = new NeuronsNetwork();
        network.setW(W);
        network.setWH(WH);
        teacher.setNetwork(network);
    }

    @Test
    public void testNormalizeW() throws Exception{

    }
}
