import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{
    public Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Window(){
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ((Graphics2D)g).drawImage(image, 0, 0, null);
            }
        };
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(panel);
        pack();
    }
}
