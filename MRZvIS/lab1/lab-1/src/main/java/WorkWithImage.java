import Jama.Matrix;

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

        NeuronsNetwork network = main.loadNetwork(new File(networkName));

        Matrix[] matrices = divider.divideOnTiles(image, tileWidth, tileHeight, 1);
        Matrix[] encoded = Arrays.stream(matrices).map(matrix -> network.zip(matrix)).toArray(Matrix[]::new);
        Matrix[] decoded = Arrays.stream(encoded).map(matrix -> network.unzip(matrix)).toArray(Matrix[]::new);

        BufferedImage decodedImage = divider.collectTilesToImage(decoded, image.getWidth(), image.getHeight(), tileWidth, tileHeight, 1);

        System.out.println("collect");
        ImageIO.write(decodedImage, "jpg", new File(imageName+"~"));
    }
}
