package image_utils;

import Jama.Matrix;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class PerformImageDataUtils {


    public static Color[][] getPixelsFromImage(BufferedImage image) {
        Color[][] colors = new Color[image.getHeight()][image.getWidth()];

        for (int column = 0; column < image.getWidth(); column++) {
            for (int row = 0; row < image.getHeight(); row++) {
                colors[row][column] = new Color(image.getRGB(column, row));
            }
        }

        return colors;
    }


    public static BufferedImage collectImageFromLineVector(Color[] colors, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        IntStream.range(0, width * height).forEach(value -> {
            int column = value % width;
            int row = value / width;

            image.setRGB(column, row, colors[value].getRGB());
        });
        return image;
    }

    public static int[] intRGBtoIntArray(int rgb) {
        Color color = new Color(rgb);
        int[] out = {color.getRed(), color.getGreen(), color.getBlue()};
        return out;
    }

    public static int[] imageToIntArray(BufferedImage image) {
        byte[] buffer = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        return IntStream.range(0, buffer.length).map(i -> 120 + buffer[i]).toArray();
    }

    public static int[] componentsArrayToRGBArray(int[] array, int componentsSize) {
        IntStream stream = IntStream.range(0, array.length / componentsSize)
                .map(operand -> IntStream
                        .range(0, componentsSize)
                        .reduce(0, (left, right) ->
                                left |= array[operand * componentsSize + right] << (2 - right) * 8
                        ));
        return stream.toArray();
    }

    public static double intComponentToDoubleComponent(int component) {
        return 2. * (double) component / 255. - 1.;
    }

    public static int doubleComponentToIntComponent(double component) {
        if(component>=1){
            return 255;
        }
        if(component<=-1){
            return 0;
        }
        return (int) Math.round((component + 1.) * 255. / 2.);
    }

    public static double[] mapRgbIntArrayToDouble(int[] arr) {
            return Arrays
                    .stream(arr).mapToDouble(value -> intComponentToDoubleComponent(value)).toArray();
    }

    public static int[] mapRgbDoubeArrayToInt(double[] arr) {
        return Arrays
                .stream(arr).mapToInt(value -> doubleComponentToIntComponent(value)).toArray();
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

    public static int[] colorToIntComponentsArray(Color color) {
        int[] arr = {color.getRed(), color.getGreen(), color.getBlue()};
        return arr;
    }

    public static Matrix colorArrayToLineVector(Color[] colorVector) {
        double[] array = Arrays.stream(colorVector).flatMapToInt(color -> Arrays.stream(colorToIntComponentsArray(color)))
                .mapToDouble(value -> intComponentToDoubleComponent(value)).toArray();
        return new Matrix(array, 1);
    }

    public static Color[] lineVectorToColorArray(Matrix colorVector) {
        int[] array = Arrays.stream(colorVector.getArray()).flatMapToDouble(Arrays::stream)
                .mapToInt(PerformImageDataUtils::doubleComponentToIntComponent)
                .toArray();
        int[] collectedIntArray = componentsArrayToRGBArray(array, 3);
        return Arrays.stream(collectedIntArray).mapToObj(Color::new).toArray(Color[]::new);
    }
}
