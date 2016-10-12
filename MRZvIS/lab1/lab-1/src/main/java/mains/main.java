package mains;

import Jama.Matrix;
import com.fasterxml.jackson.databind.ObjectMapper;
import image_utils.ImageTileDivider;
import image_utils.SaveUtils;
import network.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class main {
    public static final int TILE_WIDTH = 10;
    public static final int TILE_HEIGHT = 10;
    public static final String TEST_IMAGE_JPG = "test_image.jpg";
    public static final int N = 300;
    public static final int P = 290;
    public static final String CURRECT_NETWORK = "network-300-290.json";
    public static final String DECODED_IMAGE_0_0001_JPG = "decodedImage-0.0001.jpg";
    public static final int OFFSET = 1;

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
        double step = 0.000001;
        double E = 0.01;

        double[] arg = new ObjectMapper().readValue(new File("input.json"), double[].class);

        Thread thread = new TeachingThread((int) arg[0], (int) arg[1], (int) arg[2], (int) arg[3], TEST_IMAGE_JPG, CURRECT_NETWORK, arg[4], arg[5]);
        thread.start();
        Scanner reader = new Scanner(System.in);
        while (true) {
            String command = reader.nextLine();
            if("stop".equals(command)){
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
