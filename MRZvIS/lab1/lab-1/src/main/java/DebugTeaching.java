import Jama.Matrix;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DebugTeaching {
    public static void main(String[] args) {
        Matrix[] matrices = {new Matrix(new double[][]{{0.1, 0.2, -0.1}})};

        double accuracy = 0.0001;
        double step = 0.1;
        int N = 3;
        int P = 2;

        NeuronsNetwork network = main.initNetwork(N, P);

        NeuronsTeacher teacher = new NeuronsTeacher(matrices, N, P, step, accuracy);
        teacher.setNetwork(network);
        double E = accuracy * matrices.length + 1;
        for (int i = 0; E > accuracy * matrices.length; i++) {
            E = teacher.runEpoch();
        }
    }
}
