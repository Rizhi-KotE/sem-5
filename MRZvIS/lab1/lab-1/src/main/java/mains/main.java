package mains;

import Jama.Matrix;
import image_utils.ImageTileDivider;
import teaching.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class main {
    public static final int TILE_WIDTH = 10;
    public static final int TILE_HEIGHT = 10;
    public static final String TEST_IMAGE_JPG = "test_image.jpg";
    public static final int N = 300;
    public static final int P = 290;
    public static final String CURRECT_NETWORK = "teaching-300-290.json";
    public static final String DECODED_IMAGE_0_0001_JPG = "decodedImage-0.0001.jpg";
    public static final int OFFSET = 1;

    public static NeuronsNetwork initNetwork(int N, int p) {
        Matrix W = Matrix.random(N, p);
        W = W.times(2.).minus(new Matrix(N, p, 1.)).times(0.25);
        Matrix WH = W.transpose();
        NeuronsNetwork network = new NeuronsNetwork();
        network.setW(W);
        network.setWH(WH);
        return network;
    }


    public static void main(String[] args) throws Exception {
        String teacher_type = args[7];
        NeuronsTeacher teacher;
        switch (teacher_type) {
            case "linear":
                teacher = new NeuronsTeacher();
                break;
            case "normalize":
                teacher = new NormalizeNeuronsTeacher();
                break;
            default:
                teacher = null;
                System.err.println("unresolved type");
                System.exit(1);
        }

        String imageName = args[0];
        String networkName = args[1];
        int tileWidth = Integer.valueOf(args[2]);
        int tileHeight = Integer.valueOf(args[3]);
        int n = Integer.valueOf(args[4]);
        double step = Double.valueOf(args[5]);
        double E = Double.valueOf(args[6]);

        int p = tileWidth * tileHeight * 3;

        TeachingThread thread = new TeachingThread(n, p, tileWidth, tileHeight, imageName, networkName, step, E);
        thread.setTeacher(teacher);
        thread.start();
        Scanner reader = new Scanner(System.in);
        while (true) {
            String command = reader.nextLine();
            if ("stop".equals(command)) {
                thread.interrupt();
                break;
            }
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

        return Arrays.stream(image)
                .map(bufferedImage -> ImageTileDivider.divideOnTiles(bufferedImage, tileWidth, tileHeight, OFFSET))
                .flatMap(Arrays::stream).toArray(Matrix[]::new);
    }
}
