import Jama.Matrix;
import image_utils.ImagePerformerAndRepaer;
import image_utils.ImageTileDivider;
import image_utils.PerformImageDataUtils;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class ColectAndDivideImageTest {
    BufferedImage image;

    @Before
    public void onStart() throws IOException {
        image = ImageIO.read(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/test.png"));
    }

    @Test
    public void integrateAndDisintegrateImageTest() {
        Color[][] colorsMatrix = PerformImageDataUtils.getPixelsFromImage(image);
        Color[] colorsArray = Arrays.stream(colorsMatrix).flatMap(Arrays::stream).toArray(Color[]::new);
        BufferedImage resultImage =
                PerformImageDataUtils.collectImageFromLineVector(colorsArray, image.getWidth(), image.getHeight());
        compareImages(image, resultImage);
    }

    @Test
    public void performAndRepairImageTest() {
        Matrix performedImageLineVector = ImagePerformerAndRepaer.performImageToLineVector(image);
        BufferedImage resultImage =
                ImagePerformerAndRepaer.repairImageFromLineVector(performedImageLineVector, image.getWidth(), image.getHeight());
        compareImages(image, resultImage);
    }

    @Test
    public void divideAndCollectImageTest() {
        Matrix[] lineVectorsOfImageTiles = ImageTileDivider.divideOnTiles(image, 5, 1, 0);
        BufferedImage resultImage = ImageTileDivider.collectTilesToImage(lineVectorsOfImageTiles, image.getWidth(),
                image.getHeight(), 5, 1, 0);
        compareImages(image, resultImage);
    }

    @Test
    public void workWithImageTest() throws IOException {
        BufferedImage image = ImageIO.read(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/1470409247115339106.jpg"));
        ImageTileDivider divider = new ImageTileDivider();
        Matrix[] matrices = divider.divideOnTiles(image, 3, 1, 0);
        BufferedImage image1 = divider.collectTilesToImage(matrices, image.getWidth(), image.getHeight(), 3, 1, 0);
        compareImages(image, image1);
    }

    @Test
    public void intToDoubleAndBackTest() {
        int[] original = new int[256];
        int[] out = new int[256];
        double[] performed_value = new double[256];

        IntStream.range(0, 255).forEach(i -> {
            original[i] = i;

            performed_value[i] = PerformImageDataUtils.intComponentToDoubleComponent(i);
            out[i] = PerformImageDataUtils.doubleComponentToIntComponent(performed_value[i]);
        });
        assertArrayEquals(original, out);
    }


    public static void compareImages(BufferedImage expectedImage, BufferedImage resultImage) {
        Color[][] expectedMatrix = PerformImageDataUtils.getPixelsFromImage(expectedImage);
        Color[][] resultMatrix = PerformImageDataUtils.getPixelsFromImage(resultImage);
        assertArrayEquals(expectedMatrix, resultMatrix);
    }
}

