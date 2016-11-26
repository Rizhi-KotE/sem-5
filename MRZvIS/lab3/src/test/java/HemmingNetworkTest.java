import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;

public class HemmingNetworkTest {

    private ObjectMapper mapper = new ObjectMapper();;

    @Test
    public void testLoadImages() throws Exception {
//        List<File> files = Arrays.asList(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab3/src/main/resources/correct").listFiles());
        List<File> files = Arrays.asList(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab3/src/main/resources/correct").listFiles());
        ImagesLoader imagesLoader = new ImagesLoader(files);
        List<int[]> images = imagesLoader.getImages();
        HemmingNetwork hemmingNetwork = new HemmingNetwork(36, images);
        int[] result = hemmingNetwork.run(images.get(0));
        int[] expected = {1, 0, 0, 0, 0, 0, 0};
        assertArrayEquals(expected, result);
    }

    @Test
    public void testTeachingImages() throws Exception {
        List<File> correctFiles =
                Arrays.stream(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab3/src/main/resources/incorrect").listFiles())
                        .sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        List<File> incorrectFiles = new ArrayList<>(correctFiles);
        ImagesLoader imagesLoader = new ImagesLoader(correctFiles);
        ImagesLoader incorrectLoader = new ImagesLoader(incorrectFiles);
        List<int[]> images = imagesLoader.getImages();
        List<int[]> incorrectImages = incorrectLoader.getImages();
        HemmingNetwork hemmingNetwork = new HemmingNetwork(36, images);
        printResults(incorrectImages, hemmingNetwork);
    }


    @Test
    public void testSmallerImages() throws Exception {
        List<File> correctFiles =
                Arrays.stream(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab3/src/main/resources/smaller").listFiles())
                        .sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        List<File> incorrectFiles = new ArrayList<>(correctFiles);
        ImagesLoader imagesLoader = new ImagesLoader(correctFiles);
        ImagesLoader incorrectLoader = new ImagesLoader(incorrectFiles);
        List<int[]> images = imagesLoader.getImages();
        List<int[]> incorrectImages = incorrectLoader.getImages();
        HemmingNetwork hemmingNetwork = new HemmingNetwork(25, images);
        printResults(incorrectImages, hemmingNetwork);
    }

    private void printResults(List<int[]> incorrectImages, HemmingNetwork hemmingNetwork) throws IllegalAccessException, JsonProcessingException {
        for (int i = 0; i < incorrectImages.size(); i++) {
            int[] result = hemmingNetwork.run(incorrectImages.get(i));
            System.out.format("%s количество итераций %d\n", mapper.writeValueAsString(result), hemmingNetwork.getIterations());
        }
    }

    @Test
    public void testIncorrectImages() throws Exception {
        List<File> correctFiles =
                Arrays.stream(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab3/src/main/resources/incorrect").listFiles())
                        .sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        List<File> incorrectFiles =
                Arrays.stream(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab3/src/main/resources/correct").listFiles())
                        .sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        ImagesLoader imagesLoader = new ImagesLoader(correctFiles);
        ImagesLoader incorrectLoader = new ImagesLoader(incorrectFiles);
        List<int[]> images = imagesLoader.getImages();
        List<int[]> incorrectImages = incorrectLoader.getImages();
        HemmingNetwork hemmingNetwork = new HemmingNetwork(36, images);
        printResults(incorrectImages, hemmingNetwork);
    }


    @Test
    public void testInverseImages() throws Exception {
        List<File> correctFiles =
                Arrays.stream(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab3/src/main/resources/incorrect").listFiles())
                        .sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        List<File> inverseFiles =
                Arrays.stream(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab3/src/main/resources/inverse").listFiles())
                        .sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        ImagesLoader imagesLoader = new ImagesLoader(correctFiles);
        ImagesLoader inverse = new ImagesLoader(inverseFiles);
        List<int[]> images = imagesLoader.getImages();
        List<int[]> inverseImages = inverse.getImages();
        HemmingNetwork hemmingNetwork = new HemmingNetwork(36, images);
        for (int i = 0; i < inverseImages.size(); i++) {
            int[] result = hemmingNetwork.run(inverseImages.get(i));
            System.out.format("%s количество итераций %d\n", mapper.writeValueAsString(result), hemmingNetwork.getIterations());
        }
    }

    @Test
    public void simpleImagesTest() throws Exception {
        List<int[]> images = Arrays.asList(new int[][]{
                {1, -1, -1},
                {1, 1, 1},
                {-1, -1, -1}
        });
        HemmingNetwork hemmingNetwork = new HemmingNetwork(3, images);
        int[] result = hemmingNetwork.run(images.get(0));
        int[] expected = {1, 0, 0};
        System.out.format("%s количество итераций %d/n", mapper.writeValueAsString(result), hemmingNetwork.getIterations());
        assertArrayEquals(expected, result);
    }

    @Test
    public void simpleTestFromNeuronus() throws Exception {
        List<int[]> images = Arrays.asList(new int[][]{
                {1, -1, 1, -1, 1, -1, 1, -1, 1},
                {-1, 1, -1, 1, 1, 1, -1, 1, -1},
                {1, 1, 1, 1, -1, 1, 1, 1, 1}
        });

        int testimage[] = {1, -1, -1, -1, 1, -1, 1, -1, 1};
        HemmingNetwork hemmingNetwork = new HemmingNetwork(9, images);
        int[] result = hemmingNetwork.run(testimage);
        int[] expected = {1, 0, 0};
        assertArrayEquals(expected, result);
    }
}
