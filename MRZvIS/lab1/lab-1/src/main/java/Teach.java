import Jama.Matrix;

import java.io.File;
import java.io.IOException;

public class Teach {
    public static void main(String[] args) throws IOException {
        String image = args[0];
        int tileWidth = Integer.valueOf(args[1]);
        int tileHeight = Integer.valueOf(args[2]);
        int N = Integer.valueOf(args[3]);
        int p = Integer.valueOf(args[4]);
        double step = Double.valueOf(args[5]);
        double accuracy = Double.valueOf(args[6]);

        Matrix[] matrices = main.getSamples(tileWidth, tileHeight, new File(image));

        NeuronsNetwork network = main.initNetwork(N, p);

        double[][] list = main.teachWithGraphics(matrices, network, step, accuracy);

        main.saveNetwork(new File("network-" + N + "-" + p + ".json"), network);
        main.savePlot(new File("network-" + N + "-" + p + ".plot"), list);
    }
}
