package network;

import Jama.Matrix;
import image_utils.SaveUtils;
import mains.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeachingThread extends Thread {

    private int n;
    private int p;
    private List<double[]> list;
    private Matrix[] matrices;
    private int tileWidth;
    private int tileHeight;
    private String image;
    private String currentNetwork;
    private final double step;
    private final double e;
    private ResultTeaching resultTeaching1;
    private NeuronsNetwork network;
    private NormalizeNeuronsTeacher teacher;

    public TeachingThread(int n, int p, int tileWidth, int tileHeight, String image, String currentNetwork, double step, double e) {
        this.n = n;
        this.p = p;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.image = image;
        this.currentNetwork = currentNetwork;
        this.step = step;
        this.e = e;
    }

    @Override
    public void run() {
        try {
            matrices = main.getSamples(tileWidth, tileHeight, new File(image));
        } catch (IOException e) {
            this.interrupt();
        }

        network = SaveUtils.loadNetwork(new File(currentNetwork));
        if (network.getW() == null) {
            network = main.initNetwork(n, p);
        }

        teacher = new NormalizeNeuronsTeacher();
        teacher.setN(n);
        teacher.setStep(step);
        teacher.setE(e);
        teacher.setNetwork(network);
        try {
            teacher.runTeaching();
        } catch (Exception e) {
            e.printStackTrace();
            interrupt();
        } finally {
            saveResults(network, teacher.getPlot(), teacher.getResultTeaching());
            System.exit(0);
        }
    }

    public void saveResults(NeuronsNetwork network, List<double[]> list, ResultTeaching resultTeaching) {
        SaveUtils.saveNetwork(new File(currentNetwork), network);
        try {
            SaveUtils.savePlot(new File(currentNetwork + ".plot"), list.toArray(new double[list.size()][2]));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        SaveUtils.saveObject(new File(currentNetwork + "-" + e + ".result"), resultTeaching);
    }

    @Override
    public void interrupt() {
        System.out.println("Interrupt");
        saveResults(network, list, resultTeaching1);
        stop();
    }
}
