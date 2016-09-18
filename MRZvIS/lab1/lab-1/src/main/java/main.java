import Jama.Matrix;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;

public class main {
    public static ColorModel COLOR_MODEL;

    public static void saveNetwork(File file, NeuronsNetwork network) {
        double[][][] matrices = {network.getW().getArray(), network.getWH().getArray()};
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, matrices);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static NeuronsNetwork loadNetwork(File file) {
        ObjectMapper mapper = new ObjectMapper();
        NeuronsNetwork network = new NeuronsNetwork();
        try {
            double[][][] matrices = mapper.readValue(file, double[][][].class);
            network.setW(new Matrix(matrices[0]));
            network.setWH(new Matrix(matrices[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return network;
    }

    public static NeuronsNetwork initNetwork(int N, int p) {
        Matrix W = Matrix.random(N, p);
        W = W.times(2.).minus(new Matrix(N, p, 1.));

        Matrix WH = W.transpose();
        NeuronsNetwork network = new NeuronsNetwork();
        network.setW(W);
        network.setWH(WH);
        return network;
    }

    public static void main(String[] args) throws Exception {
//        debugTeaching();
        teach(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/lab-1/network-path-to-1e-8.json"));
//        decodeEncode(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/lab-1/network-path-to-1e-8.json"));
    }

    private static void debugTeaching() throws Exception {
        BufferedImage image = ImageIO.read(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/1470409247115339106.jpg"));
        ImageTileDivider divider = new ImageTileDivider();

        //Matrix[] matrices = divider.divideOnTiles(image, 1, 1, 0);
        Matrix[] matrices = new Matrix[]{new Matrix(new double[][]{{0.1, 0.2, 0.3}})};

        NeuronsNetwork network = initNetwork(3, 2);

        NeuronsTeacher teacher = new NeuronsTeacher(matrices, 3, 2, 0.00000001, 0.00004);
        teacher.setNetwork(network);
        double E = 1;
        double lastE = 1;
        for (int i = 0; E > 0.4; i++) {
            lastE = E;
            E = teacher.nextStep();
            if (lastE > E) {
                System.out.println(i + ", " + E);
            } else {
                break;
            }

        }
        System.out.println("stop");
    }

    private static void decodeEncode(File file) throws Exception {
        NeuronsNetwork network = loadNetwork(file);

        BufferedImage image = ImageIO.read(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/1470409247115339106.jpg"));
        ImageTileDivider divider = new ImageTileDivider();

        Matrix[] matrices = divider.divideOnTiles(image, 1, 1, 0);
        Matrix[] encoded = Arrays.stream(matrices).map(matrix -> network.zip(matrix)).toArray(Matrix[]::new);
        Matrix[] decoded = Arrays.stream(encoded).map(matrix -> network.unzip(matrix)).toArray(Matrix[]::new);

        BufferedImage decodedImage = divider.collectTilesToImage(decoded, image.getWidth(), image.getHeight(), 1, 1, 0);

        System.out.println("collect");
        ImageIO.write(decodedImage, "jpg", new File("decodedImage-0.0001.jpg"));
//        Window window1 = new Window();
//        window1.addImage(image);
//        Window window2 = new Window();
//        window2.addImage(decodedImage);
    }

    public static void teach(File file) throws IOException {
        NeuronsNetwork network = loadNetwork(file);
        if (network.getW() == null) {
            network = initNetwork(3, 2);
        }

        BufferedImage image = ImageIO.read(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/1470409247115339106.jpg"));
        ImageTileDivider divider = new ImageTileDivider();

        Matrix[] matrices = divider.divideOnTiles(image, 1, 1, 0);

        NeuronsTeacher teacher = new NeuronsTeacher(matrices, 3, 1, 0.00000001, 0.4);
        teacher.setNetwork(network);
        double E = 1;
        double lastE = 1;
        for (int i = 0; E > 0.0004; i++) {
            E = teacher.stepOfTeaching();
        }
        if (!Double.isNaN(network.getW().getArray()[0][0])) {
            System.out.println("save");
            saveNetwork(file, network);
        }
        E = teacher.stepOfTeaching();
    }

}
