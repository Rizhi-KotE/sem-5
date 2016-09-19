import Jama.Matrix;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

        ResultTeaching result = new ResultTeaching(0, 0, 0, N, p, matrices.length, accuracy, step);


        List<double[]> list = null;
        main.teachWithGraphics(matrices, network, step, accuracy, result, list);

        main.saveNetwork(new File("network-" + N + "-" + p + "-" + accuracy + ".json"), network);
        main.savePlot(new File("network-" + N + "-" + p + "-" + accuracy + ".plot"), list.toArray(new double[list.size()][2]));
        main.saveObject(new File("resulr-" + N + "-" + p + "-" + accuracy + ".result"), result);
    }
}
