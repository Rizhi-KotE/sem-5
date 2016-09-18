import Jama.Matrix;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;

public class main {
    public static ColorModel COLOR_MODEL;

    public class TwoMatrix{

    }
    public static void main(String[] args) throws Exception {

        Matrix W = Matrix.random(12,4);
        Matrix WH = W.transpose();
        NeuronsNetwork network = new NeuronsNetwork();
        network.setW(W);
        network.setWH(WH);

        BufferedImage image = ImageIO.read(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/1470409247115339106.jpg"));
        ImageTileDivider divider = new ImageTileDivider();

        Matrix[] matrices = divider.divideOnTiles(image, 2, 2, 0);

        NeuronsTeacher teacher = new NeuronsTeacher(matrices, 12, 4, 0.01, 0.4);
        teacher.setNetwork(network);

//        System.out.println(mapper.writeValueAsString(teacher.getNetwork()));
        System.out.println("start");
        for (int i = 0; i < 10000; i++) {
            System.out.println(teacher.nextStep());
//            System.out.println(mapper.writeValueAsString(teacher.getNetwork()));
            if (Double.isNaN(teacher.getNetwork().getW().getArray()[0][0])) {
                System.out.println("Step " + i + "failed");
                return;
            }
        }
    }
}
