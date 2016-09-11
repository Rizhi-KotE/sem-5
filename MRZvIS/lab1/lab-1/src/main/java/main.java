import Jama.Matrix;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class main {
    public static void main(String[] args) throws Exception{
        Image image = ImageIO.read(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/1470409247115339106.jpg"));
        Window window = new Window();
        window.setImage(image);
        window.pack();
        window.setSize(new Dimension(image.getWidth(null), image.getHeight(null)));
        window.setVisible(true);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
