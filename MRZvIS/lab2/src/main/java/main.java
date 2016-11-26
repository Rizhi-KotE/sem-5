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
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static java.lang.Math.pow;


public class main {


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose sequence\n" +
                "1. PowerSequence\n" +
                "2. Cos\n" +
                "3. LinearSequnce\n" +
                "4. FibonachiSequence\n" +
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
        Sequence sequence;
        SequenceNormalizer sequenceNormalizer = null;
        switch (sequenceType) {
            case 1:
                expectedError = 1e-5;
                step = 0.0002;
                size = 25;
                predictedLength = 10;
                sequence = new PowerSecuence(size);
                sequenceNormalizer = new SequenceNormalizer(sequence) {
                    @Override
                    public double restore(double value) {
                        return value;
                    }

                    @Override
                    public DoubleStream getSequence() {
                        return sequence.getSequence();
                    }
                };
                ;
                runPrediction(inputVectorSize, step, expectedError, predictedLength, network, sequenceNormalizer);
                break;
            case 2:
                sequence = new CosinSequence(size);
                sequenceNormalizer = new SequenceNormalizer(sequence) {
                    @Override
                    public double restore(double value) {
                        return value;
                    }

                    @Override
                    public DoubleStream getSequence() {
                        return sequence.getSequence();
                    }
                };
                runPrediction(inputVectorSize, step, expectedError, predictedLength, network, sequenceNormalizer);
                break;
            case 3:
                size = 30;
                predictedLength = 5;
                expectedError /= 10;
                sequence = new LinearSequnce(size);
                sequenceNormalizer = new SequenceNormalizer(sequence);
                runPrediction(inputVectorSize, step, expectedError, predictedLength, network, sequenceNormalizer);
                break;
            case 4:
                size = 15;
                step = 0.0005;
                sequence = new FibonachiSequence(size);
                predictedLength = 5;
                expectedError /= 100;
                sequenceNormalizer = new SequenceNormalizer(sequence);
//                network = new JordansNetworkWithTangens(inputVectorSize);
                runPrediction(inputVectorSize, step, expectedError, predictedLength, network, sequenceNormalizer);
                break;
            case 5:
                FileSequnce fileSequnce = new FileSequnce(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab2/src/main/resources/repli/pogoda.sequnce"));
                size = 184;
                predictedLength = 48;
                sequenceNormalizer = new SequenceNormalizer(fileSequnce) {
                    @Override
                    public double normalize(double value) {
                        return pow(value, 0.5);
                    }

                    @Override
                    public double restore(double value) {
                        return pow(value, 2);
                    }
                };
                runPrediction(inputVectorSize, step, expectedError, predictedLength, network, sequenceNormalizer);
                break;
            default:
                sequence = null;
                sequenceNormalizer = null;
                System.exit(1);
        }
    }

    private static void runPrediction(int inputVectorSize, double step, double expectedError, int predictedLength, RecurentNetwork network, SequenceNormalizer sequence) {
        TrainingSetGenerator generator = new TrainingSetGenerator(sequence);
        List<Pair<Matrix, Matrix>> trainigSet = generator.createTrainingSet(inputVectorSize).collect(Collectors.toList());
        List<Pair<Matrix, Matrix>> teachingSequence = trainigSet.subList(0, trainigSet.size() - predictedLength);
        List<Pair<Matrix, Matrix>> predictedSequence = new ArrayList<>(trainigSet.subList(trainigSet.size() - predictedLength, trainigSet.size()));
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
                error += pow(result - expected, 2);
            }
            points.add(new Point.Double(iteration, error));
            System.out.println(error + " " + iteration);
            if (error < expectedError) break;
        }
        writePlot(points, "e_to_iterations");

        network.resetContext();
        Matrix lastResult = teachingSequence.get(teachingSequence.size() - 1).getValue();
        List<Point.Double> prediction = new ArrayList();
        for (Pair<Matrix, Matrix> pair : teachingSequence) {
            lastResult = network.straightPropagation(pair.getKey());
            prediction.add(new Point2D.Double(sequence.restore(pair.getValue().get(0, 0)),
                    sequence.restore(lastResult.get(0, 0))));
        }
        Matrix futureVector = teachingSequence.get(teachingSequence.size() - 1).getKey();
        for (int i = 0; i < predictedLength; i++) {
            futureVector.setMatrix(0, 0, 0, inputVectorSize - 1 - 1, futureVector.getMatrix(0, 0, 1, inputVectorSize - 1));
            futureVector.setMatrix(0, 0, inputVectorSize - 1, inputVectorSize - 1, lastResult);
            lastResult = network.straightPropagation(futureVector);
            Point2D.Double point = new Point2D.Double(sequence.restore(predictedSequence.get(i).getValue().get(0, 0)), sequence.restore(lastResult.get(0, 0)));
            prediction.add(point);
            System.out.println(point);
        }
//        Iterator<Pair<Matrix, Matrix>> iterator = predictedSequence.iterator();
//        for (int i = 0; i < size && iterator.hasNext(); i++) {
//            Pair<Matrix, Matrix> next = iterator.next();
////            predicate.setMatrix(0, 0, 0, inputVectorSize - 1 - 1, predicate.getMatrix(0, 0, 1, inputVectorSize - 1));
//            result = network.straightPropagation(next.getKey()).get(0, 0);
//            prediction.add(new Point2D.Double(sequenceNormalizer.restore(next.getValue().get(0, 0)),
//                    sequenceNormalizer.restore(result)));
//            System.out.println("Expected " + sequenceNormalizer.restore(next.getValue().get(0, 0)) +
//                    " ,result is " + sequenceNormalizer.restore(result));
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
