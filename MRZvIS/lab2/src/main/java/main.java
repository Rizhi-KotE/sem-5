import Jama.Matrix;
import javafx.util.Pair;
import sequnce.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.IntToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;


public class main {


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose sequence\n" +
                "1. Factorial\n" +
                "2. Cos\n" +
                "3. Sin\n" +
                "4. Fibonachi\n" +
                "5. File");
        int sequence = scanner.nextInt();
//        int sequence = 5;
        int inputVectorSize = 20;
        double step = 0.001;
        double expectedError = 0.001;
        int size = 200;
        JordansNetworkWithTangens network = new JordansNetworkWithTangens(inputVectorSize);
        IntToDoubleFunction function;
        switch (sequence) {
            case 1:
                Factorial factorial = new Factorial();
                function = (x) -> 1. / factorial.applyAsDouble(x);
                expectedError = 0.0001;
                step = 0.0001;
                break;
            case 2:
                function = (x) -> Math.cos(0.5 * x);
                break;
            case 3:
                function = (x) -> Math.sin(x);
                break;
            case 4:
                function = new Fibonachi();
                break;
            case 5:
                FileSequnce fileSequnce = new FileSequnce(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab2/src/main/resources/pogoda.sequnce"));
                function = (x) -> fileSequnce.applyAsDouble(x) / 8;
                size = 40;
                break;
            default:
                function = null;
                System.exit(1);
        }
        SequenceNormalizer sequenceNormalizer = new SequenceNormalizer(IntStream.range(0, size).mapToDouble(function));
        runNetworkWork(network, sequenceNormalizer.normalize(), size, inputVectorSize, step, expectedError);
        List<Point.Double> prediction = new ArrayList<>();
        Matrix predicate = sequence.get(sequence.size() - 1).getKey();
        for (int i = 0; i < maxSize; i++) {
            predicate.setMatrix(0, 0, 0, inputVectorSize - 1 - 1, predicate.getMatrix(0, 0, 1, inputVectorSize - 1));
//            for (Pair<Matrix, Matrix> pair : sequence.subList(50, sequence.size())) {
            result = network.straightPropagation(predicate).get(0, 0);
            prediction.add(new Point2D.Double(i, sequenceNormalizer.denormilize(result)));
            System.out.println(/*"Expected " + pair.getValue().get(0, 0) + */" ,result is " + result);
        }
//        }
        writePlot(prediction, "prediction");
    }

    private static RecurentNetwork runNetworkWork(RecurentNetwork network, DoubleStream stream, int maxSize, int inputVectorSize,
                                                  double step, double expectedError) {
        TrainingSetGenerator generator = new TrainingSetGenerator(stream);
        List<Pair<Matrix, Matrix>> sequence = generator.createTrainingSet(inputVectorSize).collect(Collectors.toList());
        List<Pair<Matrix, Matrix>> check = sequence;
        LinkedList<Point.Double> points = new LinkedList<>();
        int maxIterations = 20000;
        double result;
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            network.resetContext();
            for (Pair<Matrix, Matrix> pair : check) {

                Matrix matrix = network.straightPropagation(pair.getKey());
                Matrix delta = matrix.minus(pair.getValue());
                network.backPropagate(delta, step);
            }

            double error = 0;
            network.resetContext();
            for (Pair<Matrix, Matrix> pair : check) {

                result = network.straightPropagation(pair.getKey()).get(0, 0);
                double expected = pair.getValue().get(0, 0);
                error += Math.pow(result - expected, 2);
            }
            points.add(new Point.Double(iteration, error));
            System.out.println(error + " " + iteration);
            if (error < expectedError) break;
        }
        writePlot(points, "plot");
        return network;
    }

    private static void writePlot(List<Point.Double> points, String fileName) {
        String filePath = "/home/rizhi-kote/Student/sem-5/MRZvIS/lab2/src/main/resources/" +
                fileName + ".data";
        File file = new File(filePath);
        int number = 0;
        while (file.exists()) {
            filePath = "/home/rizhi-kote/Student/sem-5/MRZvIS/lab2/src/main/resources/" +
                    fileName + number++ + ".data";
            file = new File(filePath);
        }
        try (FileWriter fileWriter = new FileWriter(file);) {
            StringBuilder builder = new StringBuilder();
            for (Point.Double point : points) {
                builder.append(point.x);
                builder.append(" ");
                builder.append(point.y);
                builder.append("\n");
            }
            fileWriter.write(builder.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
