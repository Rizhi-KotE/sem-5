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
        int sequenceType = scanner.nextInt();
//        int sequence = 5;
        System.out.println("input vector size");
        int inputVectorSize = scanner.nextInt();
        double step = 0.001 / inputVectorSize;
        double expectedError = 0.001;
        int predictedLength = inputVectorSize * 2;
        int size = 50;
        size = size + predictedLength;
        RecurentNetwork network = new JordansNetworkWithTangensNormalized(inputVectorSize);
        IntToDoubleFunction function;
        SequenceNormalizer sequenceNormalizer = null;
        switch (sequenceType) {
            case 1:
                function = new Factorial();
                expectedError = 0.0001;
                step = 0.0001;
                size = 40;
                predictedLength = 10;
                sequenceNormalizer = new SequenceNormalizer(IntStream.range(0, size).mapToDouble(function));
                break;
            case 2:
                function = (x) -> Math.cos(0.5 * x);
                sequenceNormalizer = new SequenceNormalizer(IntStream.range(0, size).mapToDouble(function)) {
                    @Override
                    public double denormilize(double value) {
                        return value;
                    }

                    @Override
                    public DoubleStream normalize() {
                        return stream;
                    }
                };
                break;
            case 3:
                function = (x) -> Math.sin(x);
                sequenceNormalizer = new SequenceNormalizer(IntStream.range(0, size).mapToDouble(function)) {
                    @Override
                    public double denormilize(double value) {
                        return value;
                    }

                    @Override
                    public DoubleStream normalize() {
                        return stream;
                    }
                };
                break;
            case 4:
                function = new Fibonachi();
                size = 40;
                predictedLength = 10;
                expectedError /= 1_000_000.;
                sequenceNormalizer = new SequenceNormalizer(IntStream.range(0, size).mapToDouble(function));
                break;
            case 5:
                FileSequnce fileSequnce = new FileSequnce(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab2/src/main/resources/repli/pogoda.sequnce"));
                function = (x) -> fileSequnce.applyAsDouble(x) / 8;
                size = 40;
                break;
            default:
                function = null;
                sequenceNormalizer = null;
                System.exit(1);
        }
        TrainingSetGenerator generator = new TrainingSetGenerator(sequenceNormalizer.normalize());
        List<Pair<Matrix, Matrix>> sequence = generator.createTrainingSet(inputVectorSize).collect(Collectors.toList());
        List<Pair<Matrix, Matrix>> teachingSequence = sequence.subList(0, sequence.size() - predictedLength);
        List<Pair<Matrix, Matrix>> predictedSequence = new ArrayList<>(sequence.subList(sequence.size() - predictedLength, sequence.size()));
        LinkedList<Point.Double> points = new LinkedList<>();
        int maxIterations = 1_000_000;
        double result;
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            network.resetContext();
            for (Pair<Matrix, Matrix> pair : teachingSequence) {

                Matrix matrix = network.straightPropagation(pair.getKey());
                Matrix delta = matrix.minus(pair.getValue());
                network.backPropagate(delta, step);
            }

            double error = 0;
            network.resetContext();
            for (Pair<Matrix, Matrix> pair : teachingSequence) {

                result = network.straightPropagation(pair.getKey()).get(0, 0);
                double expected = pair.getValue().get(0, 0);
                error += Math.pow(result - expected, 2);
            }
            points.add(new Point.Double(iteration, error));
            System.out.println(error + " " + iteration);
            if (error < expectedError) break;
        }
        writePlot(points, "e_to_iterations");

//        network.resetContext();
        Matrix lastResult = teachingSequence.get(teachingSequence.size() - 1).getValue();
        List<Point.Double> prediction = new ArrayList();
        for (Pair<Matrix, Matrix> pair : teachingSequence) {
            lastResult = network.straightPropagation(pair.getKey());
            prediction.add(new Point2D.Double(pair.getValue().get(0, 0), lastResult.get(0, 0)));
        }
        Matrix futureVector = teachingSequence.get(teachingSequence.size() - 1).getKey();
        for (int i = 0; i < predictedLength; i++) {
            futureVector.setMatrix(0, 0, 0, inputVectorSize - 1 - 1, futureVector.getMatrix(0, 0, 1, inputVectorSize - 1));
            futureVector.setMatrix(0, 0, inputVectorSize - 1, inputVectorSize - 1, lastResult);
            lastResult = network.straightPropagation(futureVector);
            Point2D.Double point = new Point2D.Double(sequenceNormalizer.denormilize(predictedSequence.get(i).getValue().get(0, 0)), sequenceNormalizer.denormilize(lastResult.get(0, 0)));
            prediction.add(point);
            System.out.println(point);
        }
//        Iterator<Pair<Matrix, Matrix>> iterator = predictedSequence.iterator();
//        for (int i = 0; i < size && iterator.hasNext(); i++) {
//            Pair<Matrix, Matrix> next = iterator.next();
////            predicate.setMatrix(0, 0, 0, inputVectorSize - 1 - 1, predicate.getMatrix(0, 0, 1, inputVectorSize - 1));
//            result = network.straightPropagation(next.getKey()).get(0, 0);
//            prediction.add(new Point2D.Double(sequenceNormalizer.denormilize(next.getValue().get(0, 0)),
//                    sequenceNormalizer.denormilize(result)));
//            System.out.println("Expected " + sequenceNormalizer.denormilize(next.getValue().get(0, 0)) +
//                    " ,result is " + sequenceNormalizer.denormilize(result));
//        }
//        }
        writePlot(prediction, "prediction");
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
