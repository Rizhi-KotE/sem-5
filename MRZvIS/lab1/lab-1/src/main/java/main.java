import Jama.Matrix;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static java.util.OptionalDouble.empty;

public class main {
    public static final int TILE_WIDTH = 8;
    public static final int TILE_HEIGHT = 8;
    public static final String TEST_IMAGE_JPG = "test_image.jpg";
    public static final int N = TILE_WIDTH * TILE_HEIGHT * 3;
    public static final int P = 170;
    public static final String CURRECT_NETWORK = "Network-" + N + "-" + P + ".json";
    public static final String DECODED_IMAGE_0_0001_JPG = "decodedImage-0.0001.jpg";
    public static final int OFFSET = 5;
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
        W = W.times(2.).minus(new Matrix(N, p, 1.)).times(0.00025);
        Matrix WH = W.transpose();
        NeuronsNetwork network = new NeuronsNetwork();
        network.setW(W);
        network.setWH(WH);
        return network;
    }


    public static void main(String[] args) throws Exception {
        Matrix[] matrices = getSamples(TILE_WIDTH, TILE_HEIGHT, new File(TEST_IMAGE_JPG));

        NeuronsNetwork network = loadNetwork(new File(CURRECT_NETWORK));
        if (network.getW() == null) {
            network = initNetwork(N, P);
        }

        //Matrix[] in = Arrays.asList(matrices).subList(0, 2).toArray(new Matrix[2]);
//        teach(matrices, network, 0.0000001, 0.008);
        double[][] list = teachWithGraphics(matrices, network, 0.0000001, 0.008);

//        decodeEncode(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/lab-1/network.json"));
        saveNetwork(new File(CURRECT_NETWORK), network);
        savePlot(new File(CURRECT_NETWORK+".plot"),list);
    }

    public static void savePlot(File file, double[][] list) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, list);
    }

    public static double[][] teachWithGraphics(Matrix[] matrices, NeuronsNetwork network, double step, double accuracy) {
        int N = network.getW().getColumnDimension();
        int p = network.getW().getRowDimension();

        NeuronsTeacher teacher = new NeuronsTeacher(matrices, N, p, step, accuracy);
        teacher.setNetwork(network);
        List<double[]> list = new ArrayList<>();
        double E = accuracy + 1;
        for (int i = 0; E > accuracy; i++) {
            E = teacher.stepOfTeaching();
            if (i % 100 == 100 - 1) {
                list.add(new double[]{i, E});
            }
        }
        System.out.print("Finish. E: " + E);
        return list.toArray(new double[list.size()][2]);
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

    public static void teach(Matrix[] matrices, NeuronsNetwork network, double step, double accuracy) throws IOException, InterruptedException {

        int N = network.getW().getColumnDimension();
        int p = network.getW().getRowDimension();

        NeuronsTeacher teacher = new NeuronsTeacher(matrices, N, p, step, accuracy);
        teacher.setNetwork(network);
        double E = accuracy + 1;
        for (int i = 0; E > accuracy; i++) {
            E = teacher.stepOfTeaching();
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
}
