import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Gallery extends JPanel {
    void addImage(BufferedImage image) {
        setLayout(new GridLayout(2, 2));
        JPanel panel = new ImagePanel(image);
        add(panel);
        validate();
    }

    class ImagePanel extends JPanel {

        private BufferedImage image;

        ImagePanel(BufferedImage image) {
            this.image = image;
            setMinimumSize(new Dimension(image.getWidth(), image.getHeight()));
            setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        }

        protected void paintComponent(Graphics g) {
            g.drawImage(image, 0, 0, null);
        }
    }
}
