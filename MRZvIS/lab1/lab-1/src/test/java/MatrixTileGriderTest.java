import Jama.Matrix;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;

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
        Point[] result = {
                new Point(0, 0), new Point(1, 0),
                new Point(0, 2), new Point(1, 2)
        };
        Point[] out = grider.getTileGrid(3, 5, 2, 3, 1);
        assertArrayEquals(out, result);
    }



    @Test
    public void getTileGridTest2() throws Exception {
        MatrixTileGrider grider = new MatrixTileGrider();
        Point[] result = {
                new Point(0, 0), new Point(2, 0), new Point(4, 0), new Point(6, 0),
                new Point(0, 2), new Point(2, 2), new Point(4, 2), new Point(6, 2),
                new Point(0, 5), new Point(2, 5), new Point(4, 5), new Point(6, 5)};
        Point[] out = grider.getTileGrid(8, 8, 2, 3, 1);
        assertArrayEquals(out, result);
    }

    double[][] m;
    Matrix matrix;
    double[][][] r;
    Matrix[] result;

    {
        m = new double[][]
                {

                        {1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9},
                        {10, 11, 12},
                        {13, 14, 15}

                }

        ;
        matrix = new

                Matrix(m);

        r = new double[][][]

                {
                        {
                                {
                                        1, 2
                                },
                                {
                                        4, 5
                                },
                                {
                                        7, 8
                                }
                        },
                        {
                                {
                                        2, 3
                                },
                                {
                                        5, 6
                                },
                                {
                                        8, 9
                                }
                        },
                        {
                                {
                                        7, 8
                                },
                                {
                                        10, 11
                                },
                                {
                                        13, 14
                                }
                        },
                        {
                                {
                                        8, 9
                                },
                                {
                                        11, 12
                                },
                                {
                                        14, 15
                                }
                        }
                };
        result = Arrays.stream(r).map(Matrix::new).toArray(Matrix[]::new);
    }

    @Test
    public void collectTileMatrix() throws Exception {

        MatrixTileGrider grider = new MatrixTileGrider();
        Matrix out = grider.collectMatrix(3, 5, 2, 3, 1, result);
        double[][] outT = out.getArray();
        assertArrayEquals(m, outT);
    }

    @Test
    public void colectTileMatrix() throws Exception {
        MatrixTileGrider grider = new MatrixTileGrider();

        Matrix[] result = Arrays.stream(r).map(Matrix::new).toArray(Matrix[]::new);
        Matrix[] out = grider.getTiles(matrix, 2, 3, 1);

        double[][][] outT = Arrays.stream(out).map(Matrix::getArray).toArray(double[][][]::new);
        assertArrayEquals(outT, r);
    }
}