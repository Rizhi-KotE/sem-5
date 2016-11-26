package sequnce;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.IntToDoubleFunction;
import java.util.stream.DoubleStream;

public class FileSequnce implements Sequence {
    private Scanner scanner;

    public FileSequnce(File file) {
        try {
            scanner = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DoubleStream getSequence() {
        List<Double> doubles = new ArrayList<>();
        while (scanner.hasNext()){
            doubles.add(Double.valueOf(scanner.next()));
        }
        return doubles.stream().mapToDouble(Double::doubleValue);
    }
}
