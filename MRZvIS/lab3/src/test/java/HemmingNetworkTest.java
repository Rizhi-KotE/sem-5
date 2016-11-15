import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;

public class HemmingNetworkTest {
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
        for (int i = 0; i < incorrectImages.size(); i++) {
            int[] result = hemmingNetwork.run(incorrectImages.get(i));
            int[] expected = {0, 0, 0, 0, 0, 0, 0};
            expected[i] = 1;
            assertArrayEquals(expected, result);
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
