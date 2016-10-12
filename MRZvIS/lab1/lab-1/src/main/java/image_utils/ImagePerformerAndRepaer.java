package image_utils;

import Jama.Matrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class ImagePerformerAndRepaer {

    public static BufferedImage repairImageFromLineVector(Matrix performedImageLineVector, int width, int height) {
        Color[] colorLineVector = PerformImageDataUtils.lineVectorToColorArray(performedImageLineVector);
        return PerformImageDataUtils.collectImageFromLineVector(colorLineVector, width, height);
    }

    public static Matrix performImageToLineVector(BufferedImage image) {
        Color[] colorVector = Arrays.stream(PerformImageDataUtils.getPixelsFromImage(image))
                .flatMap(Arrays::stream).toArray(Color[]::new);
        return PerformImageDataUtils.colorArrayToLineVector(colorVector);
    }
}
