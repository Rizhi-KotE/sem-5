import Jama.Matrix;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

public class MatrixTileGrider {
    private Matrix matrix;

    int tileWidth;
    int tileHeights;
    int offset;

    public static int[] get1DGrid(int size, int tileSize, int offset) {
        int nehvataet = calcNehvataet(size, tileSize);
        int fullSize = size + nehvataet % tileSize;
        int tilesAmount = fullSize / tileSize;
        int trueOffset = calcTrueOffset(nehvataet, offset, tilesAmount);
        return range(0, tilesAmount)
                .map(operand ->
                        calcNextPosition(operand, tileSize) - calcOffset(operand, nehvataet, trueOffset))
                .toArray();
    }

    private static int calcTrueOffset(int nehvataet, int offset, int tilesAmount) {
        if (tilesAmount * offset > nehvataet) {
            return offset;
        }
        return (int) Math.round((double) nehvataet / tilesAmount);
    }

    public Matrix[] getTiles(){
        Point[] grid = getTileGrid();
        return stream(grid)
                .map(point -> matrix.getMatrix(point.x, point.x + tileHeights - 1, point.y, point.y + tileWidth - 1))
                .toArray(Matrix[]::new);
    }

    private static int calcNehvataet(int size, int tileSize) {
        return (size % tileSize) == 0 ? 0 : tileSize - size % tileSize;
    }

    private static int calcOffset(int nomer, int nehvataet, int offset) {
        if (nomer == 0) {
            return 0;
        }
        return nehvataet > offset * nomer ? offset : nehvataet;
    }

    private static int calcNextPosition(int nomer, int tileSize) {
        return nomer * tileSize;
    }

    Point[] getTileGrid() {
        int[] horizontalGrid = get1DGrid(matrix.getColumnDimension(), getTileWidth(), offset);
        int[] verticalGrid = get1DGrid(matrix.getRowDimension(), getTileHeights(), offset);
        return get2DGrid(verticalGrid, horizontalGrid);
    }

    private Point[] get2DGrid(int[] horizontalGrid, int[] verticalGrid) {
        return stream(horizontalGrid)
                .mapToObj(i -> stream(verticalGrid)
                        .mapToObj(j -> new Point(i, j))
                        .toArray())
                .flatMap(Arrays::stream)
                .toArray(Point[]::new);
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTileHeights() {

        return tileHeights;
    }

    public void setTileHeights(int tileHeights) {
        this.tileHeights = tileHeights;
    }

    public int getTileWidth() {

        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
}
