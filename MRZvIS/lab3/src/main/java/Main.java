import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, IllegalAccessException {
        List<File> files = Arrays.asList(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab3/src/main/resources").listFiles());
        ImagesLoader imagesLoader = new ImagesLoader(files);
        List<int[]> images = imagesLoader.getImages();
        HemmingNetwork hemmingNetwork = new HemmingNetwork(36, images);
    }
}
