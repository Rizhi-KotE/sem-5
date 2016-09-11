import org.w3c.dom.css.RGBColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ImagesIO {
    public static int[] intRGBtoIntArray(int rgb) {
        Color color = new Color(rgb);
        int[] out = {color.getRed(), color.getGreen(), color.getBlue()};
        return out;
    }

    public static int[] imageToIntArray(BufferedImage image) {
        int[] pixelsArray = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        return Arrays.stream(pixelsArray).mapToObj(value -> intRGBtoIntArray(value)).flatMapToInt(Arrays::stream).toArray();
    }

    public static BufferedImage componentsArrayToImage(int[] componentsArray, int componentsSize, int width, int height) {
        int[] rgbArray = componentsArrayToRGBArray(componentsArray, componentsSize);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.getRaster().setPixels(0,0,width,height,componentsArray);
        return image;
    }

    public static int[] componentsArrayToRGBArray(int[] array, int componentsSize) {
        IntStream stream = IntStream.range(0, array.length / componentsSize)
                .map(operand -> IntStream
                        .range(0, componentsSize)
                        .reduce(0, (left, right) -> {
                            return left |= array[operand * componentsSize + right] << right*8;
                        }));
        return stream.toArray();
    }

    public static double[][] getFullImageMatrix(String filename) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
