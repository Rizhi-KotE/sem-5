import Jama.Matrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;

public class main {
    public static ColorModel COLOR_MODEL;

    public static void main(String[] args) throws Exception {
        BufferedImage image = ImageIO.read(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/1470409247115339106.jpg"));
        ImageTileDivider divider = new ImageTileDivider();
        Matrix[] matrices = divider.divideOnTiles(image, 3, 1, 0);
        BufferedImage image1 = divider.collectTilesToImage(matrices, image.getWidth(), image.getHeight(), 3, 1, 0);
        Window window = new Window();
        window.addImage(image);
        window.setVisible(true);
        Window window1 = new Window();
        window1.addImage(image1);
        window1.setVisible(true);
    }
}
