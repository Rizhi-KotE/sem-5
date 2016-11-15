import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ImagesLoader {
    public static final int OKTOTORP = 35;
    private final List<File> files;

    public ImagesLoader(List<File> files) {
        this.files = files;
    }

    public List<int[]> getImages() throws IOException {
        List<int[]> out = new ArrayList<>();
        for (File file : files) {
            if(file.isDirectory()) continue;
            int[] ints = Files.lines(file.toPath())
                    .flatMapToInt(String::chars)
                    .map(operand -> operand == OKTOTORP ? 1 : -1)
                    .toArray();
            out.add(ints);
        }
        return out;
    }
}
