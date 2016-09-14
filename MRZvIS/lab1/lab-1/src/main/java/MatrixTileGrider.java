import Jama.Matrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

public class MatrixTileGrider {

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

    public Matrix[] getTiles(Matrix matrix, int tileWidth, int tileHeights, int offset) {
        int height = matrix.getRowDimension();
        int width = matrix.getColumnDimension();
        Point[] grid = getTileGrid(width, height, tileWidth, tileHeights, offset);
        return stream(grid)
                .map(point -> matrix
                        .getMatrix(point.y, point.y + tileHeights - 1, point.x, point.x + tileWidth - 1))
                .toArray(Matrix[]::new);
    }

    public BufferedImage[] getTiles(BufferedImage image, int tileWidth, int tileHeights, int offset) {
        int height = image.getHeight();
        int width = image.getWidth();
        Point[] grid = getTileGrid(width, height, tileWidth, tileHeights, offset);
        return stream(grid)
                .map(point -> image
                        .getSubimage(point.x, point.y, tileWidth, tileHeights))
                .toArray(BufferedImage[]::new);
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

    Point[] getTileGrid(int width, int height, int tileWidth, int tileHeights, int offset) {
        int[] horizontalGrid = get1DGrid(width, tileWidth, offset);
        int[] verticalGrid = get1DGrid(height, tileHeights, offset);
        return get2DGrid(horizontalGrid, verticalGrid);
    }

    private Point[] get2DGrid(int[] horizontalGrid, int[] verticalGrid) {
        return stream(verticalGrid)
                .mapToObj(j -> stream(horizontalGrid)
                        .mapToObj(i -> new Point(i, j))
                        .toArray())
                .flatMap(Arrays::stream)
                .toArray(Point[]::new);
    }

    public BufferedImage collectMatrix(int width, int height, int tileWidth, int tileHeights, int offset, BufferedImage[] images) {
        Point[] grid = getTileGrid(width, height, tileWidth, tileHeights, offset);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        IntStream.range(0, images.length).forEach(i -> {
            Point point = grid[i];
            BufferedImage subimage = images[i];
            g2d.drawImage(subimage, point.x, point.y, tileWidth, tileHeights, null);
        });
        return image;
    }

    public Matrix collectMatrix(int width, int height, int tileWidth, int tileHeights, int offset, Matrix[] matrices) {
        Point[] grid = getTileGrid(width, height, tileWidth, tileHeights, offset);
        Matrix outMatrix = new Matrix(height, width);
        IntStream.range(0, matrices.length).forEach(i -> {
            Point point = grid[i];
            Matrix matrix = matrices[i];
            outMatrix.setMatrix(point.y, point.y + tileHeights - 1, point.x, point.x + tileWidth - 1, matrix);
        });
        return outMatrix;
    }
}
