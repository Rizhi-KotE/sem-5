package mains;

import Jama.Matrix;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ResultTeaching;
import image_utils.ImageTileDivider;
import teaching.NeuronsNetwork;
import teaching.NeuronsTeacher;
import teaching.NormalizeNeuronsTeacher;
import teaching.TeachingTask;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class WorkWithImage {

    private final String images[];

    public WorkWithImage(String[] images) {
        this.images = images;
    }

    private static final Supplier<List<Callable>> iterationToPicturesTaskGenerator = () -> {
        int tileWidth = 3;
        int tileHeight = 3;
        String images[] = {
                "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/images/hear.bmp",
                "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/images/caleidoscop.bmp",
                "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/images/checks.bmp",
                "/home/rizhi-kote/Student/sem-5/MRZvIS/lab1/report/images/rizhi-kote.bmp"};
        double step = 0.001;
        int p = 25;
        int N = tileWidth * tileHeight * 3;
        double E = 20;
        List<Callable> solvers = new ArrayList<>();
        for (String image : images) {
            Matrix[] matrices = main
                    .getSamples(tileWidth, tileHeight, new File(image));
            NeuronsNetwork neuronsNetwork = new NeuronsNetwork(N, p);
            NeuronsTeacher teacher = new NormalizeNeuronsTeacher(matrices.length, N, p, step, E, neuronsNetwork, Arrays.asList(matrices));
            Callable task = new Callable<NeuronsNetwork>(){
                @Override
                public NeuronsNetwork call() throws Exception {
                    teacher.runTeaching();
                    NeuronsNetwork network = teacher.getBestNetwork();
                    BufferedImage imageInstalce = ImageIO.read(new File(image));
                    ImageTileDivider divider = new ImageTileDivider();
                    Matrix[] matrices = divider.divideOnTiles(imageInstalce, tileWidth, tileHeight, 1);
                    Matrix[] encoded = Arrays.stream(matrices).map(matrix -> network.pack(matrix)).toArray(Matrix[]::new);
                    Matrix[] decoded = Arrays.stream(encoded).map(matrix -> network.extract(matrix)).toArray(Matrix[]::new);

                    BufferedImage decodedImage = divider.collectTilesToImage(decoded, imageInstalce.getWidth(), imageInstalce.getHeight(), tileWidth, tileHeight, 1);

                    System.out.println("collect");
                    ImageIO.write(decodedImage, "jpg", new File(image+"new.jpg"));
                    return network;
                }
            };
            solvers.add(task);
        }
        return solvers;
    };

    public static List<NeuronsNetwork> runExecution(List<Callable> solvers) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        ExecutorCompletionService<NeuronsNetwork> ecs = new ExecutorCompletionService<>(executor);
        ArrayList<Future<ResultTeaching>> futures = new ArrayList<>();
        List<NeuronsNetwork> output = new ArrayList<>();
        try {
            for (Callable solver : solvers)
                futures.add(ecs.submit(solver));
            for (int i = 0; i < futures.size(); i++)
                try {
                    NeuronsNetwork result = ecs.take().get();
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

    public static void main(String[] args) throws Exception {
        runExecution(iterationToPicturesTaskGenerator.get());
    }
}
