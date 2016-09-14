import Jama.Matrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.IntStream;;

/**
 * use to divide image on vectors array and back
 */
public class ImageTileDivider {

    private static MatrixTileGrider tileGrider = new MatrixTileGrider();

    public static Matrix[] divideOnTiles(BufferedImage image, int tileWidth, int tileHeight, int offset) {
        BufferedImage[] images = tileGrider.getTiles(image, tileWidth, tileHeight, offset);
        return Arrays.stream(images)
                .map(subimage -> ImagePerformerAndRepaer.performImageToLineVector(subimage)).toArray(Matrix[]::new);
    }


    public static BufferedImage collectTilesToImage(Matrix[] tiles, int width, int height, int tileWidth, int tileHeight, int offset) {
        BufferedImage[] images = Arrays.stream(tiles)
                .map(lineVector-> ImagePerformerAndRepaer
                        .repairImageFromLineVector(lineVector, tileWidth, tileHeight))
                .toArray(BufferedImage[]::new);
        return tileGrider.collectMatrix(width,height,tileWidth,tileHeight,offset,images);
    }

    private static int[] addForthComponent(int[] componentsArray) {
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
