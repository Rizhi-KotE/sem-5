import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MatrixTest {
    double[][] array = {{1., 2.}, {3., 4.}};
    double[] result = {1., 2., 3., 4.};

    @Test
    public void intRGBtoIntArrayTest() {
        Color color = new Color(125, 234, 255);
        int[] colorArray = ImagesIO.intRGBtoIntArray(color.getRGB());
        int[] resut = {125, 234, 255};
        assertArrayEquals(colorArray, resut);
    }

    @Test
    public void imageToIntArrayTest() {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        int[] array = {255, 255, 255};
        image.getRaster().setPixel(0, 0, array);
        image.getRaster().setPixel(0, 1, array);
        image.getRaster().setPixel(1, 0, array);
        image.getRaster().setPixel(1, 1, array);
        int[] result = ImagesIO.imageToIntArray(image);
        int[] sample = {255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
        assertArrayEquals(result, sample);
    }

    @Test
    public void intArrayToImageTest() {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_BGR);
        int[] array = {255, 255, 255};
        image.getRaster().setPixel(0, 0, array);
        image.getRaster().setPixel(0, 1, array);
        image.getRaster().setPixel(1, 0, array);
        image.getRaster().setPixel(1, 1, array);

        int[] sample = {255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
        BufferedImage result = ImagesIO.componentsArrayToImage(sample, 3, image.getWidth(), image.getHeight());

        int[] imageArr = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        int[] resultArr = ((DataBufferInt) result.getRaster().getDataBuffer()).getData();
        
        assertArrayEquals(imageArr, resultArr);
    }

    @Test
    public void componentsArrayToRGBArrayTest(){
        int[] sample = {255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
        Color color = new Color(255,255,255,0);
        int rgb = color.getRGB();
        int[] result = {rgb, rgb, rgb, rgb};
        int[] out = ImagesIO.componentsArrayToRGBArray(sample, 3);
        assertArrayEquals(out, result);
    }

    @Test
    public void to1DVectorTest() {
        List list = Arrays.asList(1, 2, 3, 4, 5);
        double[] y = Arrays.stream(array).flatMapToDouble(Arrays::stream).toArray();
        assertArrayEquals(y, result, 0.000);
    }
}
