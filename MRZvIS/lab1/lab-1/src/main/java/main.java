import Jama.Matrix;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class main {
    public static int RGB_COMPONENTS = 3;

    public static void main(String[] args) throws Exception {
        BufferedImage image = ImageIO.read(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/1470409247115339106.jpg"));
        ImageTileDivaider divaider = new ImageTileDivaider();
        Matrix[] matrices = divaider.divideOnTiles(image, 3 * RGB_COMPONENTS, 3, 0);
        BufferedImage image1 = divaider.collectTilesToImage(matrices, image.getWidth() * RGB_COMPONENTS, image.getHeight(), 3 * RGB_COMPONENTS, 3, 1);
        Window window = new Window();
        window.addImage(image);
        window.addImage(image1);
        window.setVisible(true);
    }
}
