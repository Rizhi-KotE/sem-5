package mains;

import Jama.Matrix;
import dto.ResultTeaching;
import javafx.util.Pair;
import teaching.NeuronsNetwork;
import teaching.NeuronsTeacher;
import teaching.NormalizeNeuronsTeacher;
import teaching.TeachingTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CalCompressionRatio {
    private static final Supplier<List<TeachingTask>> zipRateTaskGenerator = () -> {
        int tileWidth = 4;
        int tileHeight = 4;
        String image = "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/images/hear.bmp";
        Matrix[] matrices = main
                .getSamples(tileWidth, tileHeight, new File(image));
        double step = 0.001;
        double E = 20;
        int N = tileWidth * tileHeight * 3;
        List<TeachingTask> solvers = new ArrayList<>();
        for (int p = 28; p < N; p += 2) {
            NeuronsNetwork neuronsNetwork = new NeuronsNetwork(N, p);
            NeuronsTeacher teacher = new NormalizeNeuronsTeacher(matrices.length, N, p, step, E, neuronsNetwork, Arrays.asList(matrices));
            TeachingTask task = new TeachingTask(teacher);
            solvers.add(task);
        }
        return solvers;
    };


    private static final Function<ResultTeaching, Pair<Double, Integer>> zipRatePlotMapper = result -> {
        double compressionRate = (double) (result.getN() * result.getL()) /
                ((result.getN() + result.getL()) * result.getP() + 2);
        return new Pair<>(compressionRate, result.getEpochNumber());
    };

    private static final Supplier<List<TeachingTask>> iterationToPicturesTaskGenerator = () -> {
        int tileWidth = 4;
        int tileHeight = 4;
        String images[] = {
                "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/images/hear.bmp",
                "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/images/caleidoscop.bmp",
                "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/images/checks.bmp",
                "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/images/rizhi-kote.bmp"};
        double step = 0.001;
        int p = 38;
        int N = tileWidth * tileHeight * 3;
        double E = 20;
        List<TeachingTask> solvers = new ArrayList<>();
        for (String image : images) {
            Matrix[] matrices = main
                    .getSamples(tileWidth, tileHeight, new File(image));
            NeuronsNetwork neuronsNetwork = new NeuronsNetwork(N, p);
            NeuronsTeacher teacher = new NormalizeNeuronsTeacher(matrices.length, N, p, step, E, neuronsNetwork, Arrays.asList(matrices));
            TeachingTask task = new TeachingTask(teacher);
            solvers.add(task);
        }
        return solvers;
    };


    private static final Function<ResultTeaching, Pair<Double, String>> iterationToRicturePlotMapper =
            result -> new Pair<>(0., result.getImage() + result.getEpochNumber());

    private static final Supplier<List<TeachingTask>> expectedErrorTaskCreator = () -> {
        int tileWidth = 4;
        int tileHeight = 4;
        String image = "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/images/hear.bmp";
        Matrix[] matrices = main
                .getSamples(tileWidth, tileHeight, new File(image));
        double step = 0.001;
        int p = 38;
        int N = tileWidth * tileHeight * 3;
        List<TeachingTask> solvers = new ArrayList<>();
        for (double E = 20; E < 100; E += 10) {
            NeuronsNetwork neuronsNetwork = new NeuronsNetwork(N, p);
            NeuronsTeacher teacher = new NormalizeNeuronsTeacher(matrices.length, N, p, step, E, neuronsNetwork, Arrays.asList(matrices));
            teacher.setImage(image);
            TeachingTask task = new TeachingTask(teacher);
            solvers.add(task);
        }
        return solvers;
    };


    private static final Function<ResultTeaching, Pair<Double, Integer>> expectedErrorPlotMapper =
            result -> new Pair<>(result.getE(), result.getEpochNumber());

    private static final Supplier<List<TeachingTask>> iterationToStepTaskCreator = () -> {
        int tileWidth = 4;
        int tileHeight = 4;
        String image = "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/images/hear.bmp";
        Matrix[] matrices = main
                .getSamples(tileWidth, tileHeight, new File(image));
        int p = 38;
        int N = tileWidth * tileHeight * 3;
        double E = 20;
        List<TeachingTask> solvers = new ArrayList<>();
        for (double step = 0.001; E < 0.01; E += 0.001) {
            NeuronsNetwork neuronsNetwork = new NeuronsNetwork(N, p);
            NeuronsTeacher teacher = new NormalizeNeuronsTeacher(matrices.length, N, p, step, E, neuronsNetwork, Arrays.asList(matrices));
            teacher.setImage(image);
            TeachingTask task = new TeachingTask(teacher);
            solvers.add(task);
        }
        return solvers;
    };


    private static final Function<ResultTeaching, Pair<Double, Integer>> iterationToStepPlotMapper =
            result -> new Pair<>(result.getA(), result.getEpochNumber());



    public static void main(String[] args) throws Exception {
        Supplier<List<TeachingTask>> taskGenerator = null;
        String filename = null;
        Function<ResultTeaching, ? extends Pair<Double, ? extends Object>> plotMapper = null;
        Scanner scanner = new Scanner(System.in);
        int variant = scanner.nextInt();
        switch (variant) {
            case 1:
                taskGenerator = zipRateTaskGenerator;
                filename = "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/iter_z/plot.data";
                plotMapper = zipRatePlotMapper;
                break;
            case 2:
                taskGenerator = expectedErrorTaskCreator;
                filename = "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/iter_e/plot.data";
                plotMapper = expectedErrorPlotMapper;
                break;
            case 3:
                taskGenerator = iterationToPicturesTaskGenerator;
                filename = "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/iter_pic/plot.data";
                plotMapper = iterationToRicturePlotMapper;
                break;
            case 4:
                taskGenerator = iterationToStepTaskCreator;
                filename = "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/iter_a/plot.data";
                plotMapper = iterationToStepPlotMapper;
                break;
            default:
                System.exit(1);
        }
        List<ResultTeaching> resultTeaching = runExecution(taskGenerator);

        List<? extends Pair<Double, ? extends Object>> resultList = resultTeaching.stream().map(plotMapper).sorted((r1, r2) -> Double.compare(r1.getKey(), r2.getKey()))
                .collect(Collectors.toList());
        savePlot(resultList, filename);
    }

    private static void savePlot(List<? extends Pair<? extends Object, ? extends Object>> resultList, String filename) {
        StringBuilder builder = new StringBuilder();
        for (Pair pair : resultList) {
            builder.append(pair.getKey());
            builder.append(" ");
            builder.append(pair.getValue());
            builder.append("\n");
        }
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(builder.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<ResultTeaching> runExecution(Supplier<List<TeachingTask>> supplier) throws Exception {
        List<TeachingTask> solvers = supplier.get();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        ExecutorCompletionService<ResultTeaching> ecs = new ExecutorCompletionService<>(executor);
        ArrayList<Future<ResultTeaching>> futures = new ArrayList<>();
        List<ResultTeaching> output = new ArrayList<>();
        try {
            for (Callable solver : solvers)
                futures.add(ecs.submit(solver));
            for (int i = 0; i < futures.size(); i++)
                try {
                    ResultTeaching result = ecs.take().get();
                    if (result != null) {
                        output.add(result);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            for (Future f : futures)
                f.cancel(true);
            executor.shutdown();
        }
        return output;
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
//            TeachingTask thread = new TeachingTask(9, p, tileWidth, tileHeight, imageName, networkName, i, E);
//            thread.installTeacher(teacher);
//            thread.start();
//        }
//    }
}
