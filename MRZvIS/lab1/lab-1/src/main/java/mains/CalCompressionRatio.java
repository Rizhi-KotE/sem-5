package mains;

import Jama.Matrix;
import teaching.NeuronsTeacher;
import teaching.NormalizeNeuronsTeacher;
import teaching.TeachingThread;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CalCompressionRatio {
    //    public static void main(String[] args) throws Exception {
//        String imageName = "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/lenna.jpg";
//        int tileWidth = 4;
//        int tileHeight = 4;
//        double step = 0.001;
//        double E = 5;
//
//        int p = tileWidth * tileHeight * 3;
//
//        for (int n = 39; n < 49; n++) {
//            NeuronsTeacher teacher = new NormalizeNeuronsTeacher();
//            String networkName = "network-" + n + ".json";
//            TeachingThread thread = new TeachingThread(n, p, tileWidth, tileHeight, imageName, networkName, step, E);
//            thread.setTeacher(teacher);
//            thread.start();
//        }
//    }
    public static void main(String[] args) throws Exception {
        int tileWidth = 4;
        int tileHeight = 4;
        Matrix[] matrices = main.getSamples(tileWidth, tileHeight, new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/lenna-512.jpg"));
        double step = 0.001;
        double E = 20;

        int p = tileWidth * tileHeight * 3;

        List<Matrix> list =Arrays.asList(matrices);

        for (int i = 400; i < matrices.length; i += 300) {
            NeuronsTeacher teacher = new NormalizeNeuronsTeacher();
            String networkName = "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/iter_size/network-" + i + ".json";
            TeachingThread thread = new TeachingThread(35, p, tileWidth, tileHeight, null, networkName, step, E);
            List<Matrix> input = new ArrayList<>(list.subList(0,i));
            thread.setMatrices(input.toArray(new Matrix[i]));
            thread.setTeacher(teacher);
            thread.start();
        }
    }
//    public static void main(String[] args) throws Exception {
//        String imageName = "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/lenna-256.png";
//        int tileWidth = 2;
//        int tileHeight = 2;
//        double E = 30;
//
//        int p = tileWidth * tileHeight * 3;
//
//        for (double i = 0.0001; i <= 0.001; i+=0.0002) {
//            NeuronsTeacher teacher = new NormalizeNeuronsTeacher();
//            String networkName = "network-" + i + ".json";
//            TeachingThread thread = new TeachingThread(9, p, tileWidth, tileHeight, imageName, networkName, i, E);
//            thread.setTeacher(teacher);
//            thread.start();
//        }
//    }
}
