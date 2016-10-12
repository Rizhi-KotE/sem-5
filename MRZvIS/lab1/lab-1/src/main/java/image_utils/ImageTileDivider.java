package image_utils;

import Jama.Matrix;
import image_utils.ImagePerformerAndRepaer;

import java.awt.image.BufferedImage;
import java.util.Arrays;
;

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
}
