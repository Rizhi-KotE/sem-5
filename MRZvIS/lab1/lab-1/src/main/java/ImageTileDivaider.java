import Jama.Matrix;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.IntStream;;

/**
 * use to divide image on vectors array and back
 */
public class ImageTileDivaider {

    private MatrixTileGrider tileGrider = new MatrixTileGrider();

    public Matrix[] divideOnTiles(BufferedImage image, int tileWidth, int tileHeight, int offset) {
        int[] intArray = NeironsUtils.imageToIntArray(image);
        double[] transformedComponentsArray = NeironsUtils.mapRgbIntArrayToDouble(intArray);
        double[][] arrayToMatrix = NeironsUtils.arrayToMatrix(transformedComponentsArray, image.getHeight());
        Matrix imageComponentsMatrix = new Matrix(arrayToMatrix);

        Matrix[] matrices = tileGrider.getTiles(imageComponentsMatrix, tileWidth, tileHeight, offset);
        return Arrays.stream(matrices).map(matrix -> NeironsUtils.matrixToArray(matrix)).toArray(Matrix[]::new);
    }


    public BufferedImage collectTilesToImage(Matrix[] tiles, int width, int height, int tileWidth, int tileHeight, int offset) {
        Matrix[] matrices = Arrays.stream(tiles)
                .map(matrix -> NeironsUtils.arrayToMatrix(matrix, tileHeight)).toArray(Matrix[]::new);

        Matrix matrix = tileGrider.collectMatrix(width, height, tileWidth, tileHeight, offset, matrices);
        double[] performedComponentArray = NeironsUtils.matrixToArray(matrix.getArray());
        int[] componentsArray = NeironsUtils.mapRgbDoubeArrayToInt(performedComponentArray);
        int[] addForthComponent = addForthComponent(componentsArray);
        return NeironsUtils.componentsArrayToImage(addForthComponent, width, height);
    }

    private int[] addForthComponent(int[] componentsArray) {
        int size = componentsArray.length / 3;
        int[] out = new int[componentsArray.length + size];
        IntStream.range(0, size).forEach(i -> {
            int begin = i * 4;
            int b = i * 3;
            out[begin]=componentsArray[b];
            out[begin+1]=componentsArray[b+1];
            out[begin+2]=componentsArray[b+2];
            out[begin+3]=255;
        });
        return  out;
    }
}
