import Jama.Matrix;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class Window extends JFrame {

    private List<BufferedImage> images;
    private final Gallery gallery;
    private JFileChooser fileChooser = new JFileChooser();
    private final JFrame frame;
    private JLabel imageWidth;
    private JLabel imageHeight;
    private JTextField tileWidth;
    private JTextField tileHeight;
    private JTextField ofset;
    private NeuronsNetwork network;
    private final ImageTileDivider divider = new ImageTileDivider();
    private Thread teaching;
    private NeuronsTeacher teacher;

    {
        fileChooser.setCurrentDirectory(new File("."));
    }

    public Window() {
        JTabbedPane pane = new JTabbedPane(JTabbedPane.SCROLL_TAB_LAYOUT, JComponent.WHEN_FOCUSED);
        pane.add(initTeachTab());
//        pane.add(createZipTap());
        add(pane);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame = new JFrame();
        gallery = new Gallery();
        frame.add(gallery);
        frame.setVisible(true);
        frame.pack();
    }

    private JPanel createZipTap() {
        return null;
    }

    private JPanel initTeachTab() {


        JPanel panel = new JPanel(new GridLayout());

        JButton choseImage = new JButton("choose image");
        choseImage.addActionListener(e -> {
            int i = fileChooser.showOpenDialog(this);
            File file = fileChooser.getSelectedFile();
            chooseFileAction(file);
        });
        panel.add(choseImage);

        JPanel labels = new JPanel(new GridLayout(7, 2));
        labels.add(new JLabel("image width"));
        imageWidth = new JLabel();
        labels.add(imageWidth);
        labels.add(new JLabel("image height"));
        imageHeight = new JLabel();
        labels.add(imageHeight);
        labels.add(new JLabel("tile width"));
        tileWidth = new JTextField();
        labels.add(tileWidth);
        labels.add(new JLabel("tile width"));
        tileHeight = new JTextField();
        labels.add(tileHeight);
        labels.add(new JLabel("offset"));
        ofset = new JTextField();
        labels.add(ofset);
        labels.add(new JLabel("step"));
        JTextField step = new JTextField("0.0001");
        labels.add(step);
        labels.add(new JLabel("precisios"));
        JTextField precisios = new JTextField("0.04");
        labels.add(precisios);


        teaching = new Thread(() -> {
            double E = 0;
            for (int i = 0; E > Double.valueOf(precisios.getText()); i++) {
                E = teacher.runEpoch();
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                JOptionPane.showMessageDialog(this,
                        mapper.writeValueAsString(network.getW()));
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        });

        JButton startTeaching = new JButton("start teaching");
        startTeaching.addActionListener(e -> {
            initTeachProcess(images.get(0), Integer.valueOf(tileWidth.getText()),
                    Integer.valueOf(tileHeight.getText()), 0,
                    Double.valueOf(step.getText()),
                    Double.valueOf(precisios.getText()));
            teaching.start();
        });
        JButton stopTeaching = new JButton("stop teaching");
        stopTeaching.addActionListener(e -> {
            teaching.stop();
            ObjectMapper mapper = new ObjectMapper();
            try {
                JOptionPane.showMessageDialog(this,
                        mapper.writeValueAsString(network.getW()));
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        });
        panel.add(startTeaching);
        panel.add(stopTeaching);
        JButton saveNetwork =  new JButton("save");
        saveNetwork.addActionListener(e -> main.saveNetwork( new File("network.json"), network));
        panel.add(saveNetwork);


        panel.add(labels);


        return panel;
    }

    private void initTeachProcess(BufferedImage image, int N, int p, int offset, double step, double pecisios) {
        network = main.initNetwork(N, p);
        Matrix[] matrices = ImageTileDivider.divideOnTiles(image, N / 3, p, offset);
        teacher = new NeuronsTeacher(matrices, N, p, step, pecisios);
        teacher.setNetwork(network);
    }

    private void chooseFileAction(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            images.add(image);
            gallery.addImage(image);
            frame.pack();
            frame.revalidate();
            frame.validate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
