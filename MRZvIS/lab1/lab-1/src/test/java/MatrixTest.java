import Jama.Matrix;
import javafx.scene.control.SplitPane;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MatrixTest {
    double[][] testMatrix = {{1., 2.}, {3., 4.}};
    double[] testArrayResult = {1., 2., 3., 4.};

    @Test
    public void intArrayToMatrixTest() {
        double[][] out = NeironsUtils.arrayToMatrix(testArrayResult, 2);
        assertArrayEquals(testMatrix, out);
    }

    @Test
    public void intRGBtoIntArrayTest() {
        Color color = new Color(125, 234, 255);
        int[] colorArray = NeironsUtils.intRGBtoIntArray(color.getRGB());
        int[] resut = {125, 234, 255};
        assertArrayEquals(colorArray, resut);
    }

    @Test
    public void componentsArrayToRGBArrayTest() {
        int[] sample = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Color color = new Color(1, 2, 3);
        int rgb = color.getRGB();
        int[] result = {rgb, rgb, rgb, rgb};
        int[] out = NeironsUtils.componentsArrayToRGBArray(sample, 3);
        assertArrayEquals(out, result);
    }

    @Test
    public void to1DVectorTest() {
        List list = Arrays.asList(1, 2, 3, 4, 5);
        double[] y = Arrays.stream(testMatrix).flatMapToDouble(Arrays::stream).toArray();
        assertArrayEquals(y, testArrayResult, 0.000);
    }

    @Test
    public void mapIntRgbArrayToDouble() {
        int arr[] = {65};
        double result[] = {-1 * 0.490196078};
        double out[] = NeironsUtils.mapRgbIntArrayToDouble(arr);
        assertArrayEquals(out, result, 0.000001);
    }

    @Test
    public void mapRgbDoubleArrayToInt() {
        double arr[] = {-1 * 0.490196078};
        int result[] = {65};
        int out[] = NeironsUtils.mapRgbDoubeArrayToInt(arr);
        assertArrayEquals(out, result);
    }

    @Test
    public void mapArrayToMatrix() {
        initArrayAndMatrix(10, 12);
        double[][] outMatrix = NeironsUtils.arrayToMatrix(array, 10);
        assertArrayEquals(matrix, outMatrix);
    }

    @Test
    public void mapMatrixToArray() {
        initArrayAndMatrix(10, 12);
        double[] outArray = NeironsUtils.matrixToArray(matrix);
        assertArrayEquals(array, outArray, 0);
    }

    @Test
    public void devideOnTilesTest() {
        initArrayAndMatrix(10, 12);
        initTiles(10, 12, 2, 1);
        MatrixTileGrider grider = new MatrixTileGrider();
        Matrix[] outTiles = grider.getTiles(new Matrix(matrix), 2, 1, 0);
        double[][][] outTilesArray = Arrays.stream(outTiles).map(Matrix::getArray).toArray(double[][][]::new);
        assertArrayEquals(tiles, outTilesArray);
    }

    @Test
    public void collectTilesTest() {

    }

    double[] array;
    double[][] matrix;

    private void initArrayAndMatrix(int width, int height) {
        int size = width * height;
        array = IntStream.range(0, size).mapToDouble(value -> value + 1).toArray();
        matrix = IntStream.range(0, height).mapToObj(j ->
                IntStream.range(0, width).mapToDouble(i -> array[j * width + i]).toArray()
        ).toArray(double[][]::new);
    }

    double[][][] tiles;

    private void initTiles(int width, int height, int tileWidth, int tileHeight) {
        int numberOfTiles = width * height / (tileHeight * tileWidth);
        int tilesColumns = width / tileWidth;
        int tilesRows = height / tileHeight;
        tiles = IntStream.range(0, numberOfTiles).mapToObj(tileNumber -> {
            double[][] tile = new double[tileHeight][tileWidth];
            IntStream.range(0, tileWidth * tileHeight).forEach(cellInTile ->
            {
                int rowOfTiles = tileNumber % tilesRows;
                int columnOfTiles = tileNumber % tilesColumns;
                int tileRow = cellInTile % tileHeight;
                int tileColumn = cellInTile / tileHeight;
                int matrixRow = rowOfTiles * tileHeight + cellInTile % tileHeight;
                int matrixColumn = columnOfTiles * tileHeight + cellInTile / tileHeight;

                double matrixValue = matrix[matrixRow][matrixColumn];
                tile[tileRow][tileColumn] = matrixValue;

            });
            return tile;
        }).toArray(double[][][]::new);
    }
}
