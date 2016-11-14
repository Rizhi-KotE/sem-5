package mains;

import Jama.Matrix;
import image_utils.ImageTileDivider;
import teaching.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class main {
    public static final int TILE_WIDTH = 10;
    public static final int TILE_HEIGHT = 10;
    public static final String TEST_IMAGE_JPG = "test_image.jpg";
    public static final int N = 300;
    public static final int P = 290;
    public static final String CURRECT_NETWORK = "teaching-300-290.json";
    public static final String DECODED_IMAGE_0_0001_JPG = "decodedImage-0.0001.jpg";
    public static final int OFFSET = 1;


    public static Matrix[] getSamples(int tileWidth, int tileHeight, File... files) {
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
