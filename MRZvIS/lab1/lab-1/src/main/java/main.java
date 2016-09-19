import Jama.Matrix;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class main {
    public static final int TILE_WIDTH = 3;
    public static final int TILE_HEIGHT = 3;
    public static final String TEST_IMAGE_JPG = "test_image.jpg";
    public static final int N = TILE_WIDTH * TILE_HEIGHT * 3;
    public static final int P = 20;
    public static final String CURRECT_NETWORK = "Network-" + N + "-" + P + ".json";
    public static final String DECODED_IMAGE_0_0001_JPG = "decodedImage-0.0001.jpg";
    public static final int OFFSET = 1;
    public static ColorModel COLOR_MODEL;

    public static int stepsToFail;

    public static int getStepsToFail() {
        return stepsToFail;
    }

    public static void setStepsToFail(int steps) {
        stepsToFail = steps;
    }

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
        W = W.times(2.).minus(new Matrix(N, p, 1.)).times(0.00025);
        Matrix WH = W.transpose();
        NeuronsNetwork network = new NeuronsNetwork();
        network.setW(W);
        network.setWH(WH);
        return network;
    }


    public static void main(String[] args) throws Exception {
        Matrix[] matrices = getSamples(TILE_WIDTH, TILE_HEIGHT, new File(TEST_IMAGE_JPG));

        double step = 0.000001;
        double E = 0.01;


        String[] images = {"test_image.jpg",
                "xcril-small.jpg"};

        for (int size = 3; size < 10; size++) {
            int N = size * size * 3;
            System.out.println("Size: " + size);
            for (int P = (int) (N * 0.7); P < N * 1.2; P++) {
                System.out.println("P: " + P);
                for (int imageNum = 0; imageNum < 2; imageNum++) {
                    System.out.println("Image: " + images[imageNum]);
                    String currentNetwork = "Network-" + imageNum + "-" + N + "-" + P + ".json";
                    Thread teachingThread = new TeachingThread(N, P, size, size, images[imageNum], currentNetwork, step, E);
                    teachingThread.run();
                }
            }
        }


    }


    public static void savePlot(File file, double[][] list) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, list);
    }

    public static void teachWithGraphics(
            Matrix[] matrices,
            NeuronsNetwork network,
            double step,
            double accuracy,
            ResultTeaching resultTeaching,
            List<double[]> plot) {
        int N = network.getW().getColumnDimension();
        int p = network.getW().getRowDimension();

        NeuronsTeacher teacher = new NeuronsTeacher(matrices, N, p, step, accuracy);
        teacher.setNetwork(network);
        double E = accuracy * matrices.length + 1;
        long timeStrat = System.currentTimeMillis();
        int i = 0;
        System.out.println("Start. Expected E: " + accuracy * matrices.length);
        try {
            for (i = 0; E > accuracy * matrices.length; i++) {
                E = teacher.runEpoch();
                plot.add(new double[]{i, E});
                resultTeaching.setE(E);
                resultTeaching.setTime(System.currentTimeMillis() - timeStrat);
                resultTeaching.setEpochNumber(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long timeEnd = System.currentTimeMillis();
            resultTeaching.setE(E);
            resultTeaching.setTime(timeEnd - timeStrat);
            resultTeaching.setEpochNumber(i);
            network = teacher.getBestNetwork();
            System.out.print("Finish. E: " + teacher.getBestE());
        }
    }

    public static Matrix[] getSamples(int tileWidth, int tileHeight, File... files) throws IOException {
        BufferedImage[] image = Arrays.stream(files).map(file -> {
            try {
                return ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).toArray(BufferedImage[]::new);
        ImageTileDivider divider = new ImageTileDivider();

        return Arrays.stream(image)
                .map(bufferedImage -> ImageTileDivider.divideOnTiles(bufferedImage, tileWidth, tileHeight, OFFSET))
                .flatMap(Arrays::stream).toArray(Matrix[]::new);
    }

    public static void decodeEncode(File file) throws Exception {
        NeuronsNetwork network = loadNetwork(file);

        BufferedImage image = ImageIO.read(new File(TEST_IMAGE_JPG));
        ImageTileDivider divider = new ImageTileDivider();

        Matrix[] matrices = divider.divideOnTiles(image, TILE_WIDTH, TILE_HEIGHT, OFFSET);
        Matrix[] encoded = Arrays.stream(matrices).map(matrix -> network.zip(matrix)).toArray(Matrix[]::new);
        Matrix[] decoded = Arrays.stream(encoded).map(matrix -> network.unzip(matrix)).toArray(Matrix[]::new);

        BufferedImage decodedImage = divider.collectTilesToImage(decoded, image.getWidth(), image.getHeight(), TILE_WIDTH, TILE_HEIGHT, OFFSET);

        System.out.println("collect");
        ImageIO.write(decodedImage, "jpg", new File(DECODED_IMAGE_0_0001_JPG));
//        Window window1 = new Window();
//        window1.addImage(image);
//        Window window2 = new Window();
//        window2.addImage(decodedImage);
    }

    public static void teach(Matrix[] matrices, NeuronsNetwork network, double step, double accuracy) {

        int N = network.getW().getColumnDimension();
        int p = network.getW().getRowDimension();

        NeuronsTeacher teacher = new NeuronsTeacher(matrices, N, p, step, accuracy);
        teacher.setNetwork(network);
        double E = accuracy + 1;
        for (int i = 0; E > accuracy; i++) {
            E = teacher.runEpoch();
        }
        System.out.print("Finish. E: " + E);
    }

    public static void printNetwork(NeuronsNetwork network) {
        double[][][] matrices = {network.getW().getArray(), network.getWH().getArray()};
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(matrices));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void saveObject(File file, Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
