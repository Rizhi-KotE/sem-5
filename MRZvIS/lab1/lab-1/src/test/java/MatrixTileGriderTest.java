import Jama.Matrix;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class MatrixTileGriderTest {

    @Test
    public void devidedEntirely() throws Exception {
        int size = 8;
        int tileSize = 2;
        int offset = 0;
        int[] result = {0, 2, 4, 6};
        int[] out = MatrixTileGrider.get1DGrid(size, tileSize, offset);
        assertArrayEquals(result, out);
    }

    @Test
    public void devidedWithEnoghtOfset() throws Exception {
        int size = 5;
        int tileSize = 3;
        int offset = 1;
        int[] result = {0, 2};
        int[] out = MatrixTileGrider.get1DGrid(size, tileSize, offset);
        assertArrayEquals(result, out);
    }

    @Test
    public void devideWithNotEnoughtOffset() throws Exception {
        int size = 7;
        int tileSize = 3;
        int offset = 0;
        int[] result = {0, 2, 4};
        int[] out = MatrixTileGrider.get1DGrid(size, tileSize, offset);
        assertArrayEquals(result, out);
    }

    @Test
    public void devideWithSmallNehvataetOffset() throws Exception {
        int size = 11;
        int tileSize = 3;
        int offset = 1;
        int[] result = {0, 2, 5, 8};
        int[] out = MatrixTileGrider.get1DGrid(size, tileSize, offset);
        assertArrayEquals(result, out);
    }

    @Test
    public void devide5() throws Exception {
        int[] result = {0, 2};
        int[] out1 = MatrixTileGrider.get1DGrid(5, 3, 1);
        assertArrayEquals(out1, result);
        int[] result2 = {0, 1};
        int[] out2 = MatrixTileGrider.get1DGrid(3, 2, 1);
        assertArrayEquals(out2, result2);
    }

    @Test
    public void getTileGridTest1() throws Exception {
        Matrix matrix = new Matrix(5, 3);
        MatrixTileGrider grider = new MatrixTileGrider();
        grider.setMatrix(matrix);
        grider.setTileHeights(3);
        grider.setTileWidth(2);
        grider.setOffset(1);
        Point[] result = {
                new Point(0, 0), new Point(0, 1),
                new Point(2, 0), new Point(2, 1)
        };
        Point[] out = grider.getTileGrid();
        assertArrayEquals(out, result);
    }

    @Test
    public void getTileGridTest2() throws Exception {
        Matrix matrix = new Matrix(8, 8);
        MatrixTileGrider grider = new MatrixTileGrider();
        grider.setMatrix(matrix);
        grider.setTileHeights(3);
        grider.setTileWidth(2);
        grider.setOffset(1);
        Point[] result = {
                new Point(0, 0), new Point(0, 2), new Point(0, 4), new Point(0, 6),
                new Point(2, 0), new Point(2, 2), new Point(2, 4), new Point(2, 6),
                new Point(5, 0), new Point(5, 2), new Point(5, 4), new Point(5, 6)};
        Point[] out = grider.getTileGrid();
        assertArrayEquals(out, result);
    }
}