import Jama.Matrix;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NeironsUtils {

    public static int[] intRGBtoIntArray(int rgb) {
        Color color = new Color(rgb);
        int[] out = {color.getRed(), color.getGreen(), color.getBlue()};
        return out;
    }

    public static int[] imageToIntArray(BufferedImage image) {
        byte[] buffer = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        return IntStream.range(0, buffer.length).map(i -> 120 + buffer[i]).toArray();
    }

    public static BufferedImage componentsArrayToImage(int[] componentsArray, int width, int height) {
        int[] rgbArray = componentsArrayToRGBArray(componentsArray, main.RGB_COMPONENTS);
        BufferedImage image = new BufferedImage(width / main.RGB_COMPONENTS, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = image.getRaster();
        raster.setPixels(0, 0, width / main.RGB_COMPONENTS - 1, height - 1, componentsArray);
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
        return Arrays
                .stream(arr).mapToDouble(value -> 2. * value / 255. - 1).toArray();
    }

    public static int[] mapRgbDoubeArrayToInt(double[] arr) {
        return Arrays
                .stream(arr).mapToInt(value -> (int) ((value + 1.) * 255. / 2.)).toArray();
    }

    public static double[] matrixToArray(double[][] matrix) {
        return Arrays.stream(matrix).flatMapToDouble(Arrays::stream).toArray();
    }

    public static Matrix matrixToArray(Matrix matrix) {
        return new Matrix(matrixToArray(matrix.getArray()), 1);
    }

    static public Matrix getImageMatrix(BufferedImage image) {
        int[] imageBuffer = imageToIntArray(image);
        double[] convertedBuffer = mapRgbIntArrayToDouble(imageBuffer);
        return new Matrix(convertedBuffer, image.getHeight());
    }

    public static double[][] arrayToMatrix(double[] buffer, int width) {
        int height = buffer.length / width;
        return IntStream.range(0, height)
                .mapToObj(i -> IntStream.range(0, width).mapToDouble(j -> buffer[i * width + j])
                        .toArray()).toArray(double[][]::new);
    }

    public static Matrix arrayToMatrix(Matrix buffer, int height) {
        double array[] = Arrays.stream(buffer.getArray()).flatMapToDouble(Arrays::stream).toArray();
        return new Matrix(arrayToMatrix(array, height));
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

    public static Point[] generateTileGrid(int imageHeight, int imageWidth, int tileHeight, int tileWidth, int offset) {
        return null;
    }
}
