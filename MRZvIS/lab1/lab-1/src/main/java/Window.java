import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Window extends JFrame {


    public Window() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addImage(BufferedImage image) {
        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ((Graphics2D) g).drawImage(image, 0, 0, null);
            }
        };
        panel.setMinimumSize(new Dimension(image.getWidth(), image.getHeight()));
        panel.setSize(new Dimension(image.getWidth(), image.getHeight()));
        panel.setMaximumSize(new Dimension(image.getWidth(), image.getHeight()));
        setMinimumSize(new Dimension(image.getWidth(), image.getHeight()));
        setSize(new Dimension(image.getWidth(), image.getHeight()));
        setMaximumSize(new Dimension(image.getWidth(), image.getHeight()));
        add(panel);
        pack();
    }
}
