import Jama.Matrix;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

public class NeironsUtils {

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
        image.getRaster().setPixels(0, 0, width, height, componentsArray);
        return image;
    }

    public static int[] componentsArrayToRGBArray(int[] array, int componentsSize) {
        IntStream stream = IntStream.range(0, array.length / componentsSize)
                .map(operand -> IntStream
                        .range(0, componentsSize)
                        .reduce(0, (left, right) ->
                                left |= array[operand * componentsSize + right] << right * 8
                        ));
        return stream.toArray();
    }

    public static double[] mapRgbIntArrayToDouble(int[] arr) {
        return Arrays.stream(arr).mapToDouble(value -> 2. * value / 255. - 1).toArray();
    }

    public static int[] mapRgbDoubeArrayToInt(double[] arr) {
        return Arrays.stream(arr).mapToInt(value -> (int) ((value + 1.) * 255. / 2.)).toArray();
    }

    static public Matrix getImageMatrix(BufferedImage image) {
        int[] imageBuffer = imageToIntArray(image);
        double[] convertedBuffer = mapRgbIntArrayToDouble(imageBuffer);
        return new Matrix(convertedBuffer, image.getHeight());
    }

    public static double[][] arrayToMatrix(double[] buffer, int height) {
        return new Matrix(buffer, height).getArray();
    }

    public static void writeToFile(Matrix matrix, File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, matrix);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Matrix readFromFile(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (Matrix) mapper.readValue(file, Matrix.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Point[] generateTileGrid(int imageHeight, int imageWidth, int tileHeight, int tileWidth, int offset){
        return null;
    }
}
