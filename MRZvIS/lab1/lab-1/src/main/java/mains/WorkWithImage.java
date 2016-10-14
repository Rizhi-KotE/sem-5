package mains;

import Jama.Matrix;
import image_utils.ImageTileDivider;
import teaching.NeuronsNetwork;
import image_utils.SaveUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class WorkWithImage {
    public static void main(String[] args) throws IOException {
        String imageName = args[0];
        String networkName = args[1];
        int tileWidth = Integer.valueOf(args[2]);
        int tileHeight = Integer.valueOf(args[3]);

        BufferedImage image = ImageIO.read(new File(imageName));
        ImageTileDivider divider = new ImageTileDivider();

        NeuronsNetwork network = SaveUtils.loadNetwork(new File(networkName));

        Matrix[] matrices = divider.divideOnTiles(image, tileWidth, tileHeight, 1);
        Matrix[] encoded = Arrays.stream(matrices).map(matrix -> network.pack(matrix)).toArray(Matrix[]::new);
        Matrix[] decoded = Arrays.stream(encoded).map(matrix -> network.extract(matrix)).toArray(Matrix[]::new);

        BufferedImage decodedImage = divider.collectTilesToImage(decoded, image.getWidth(), image.getHeight(), tileWidth, tileHeight, 1);

        System.out.println("collect");
        ImageIO.write(decodedImage, "jpg", new File(imageName+"new.jpg"));
    }
}
