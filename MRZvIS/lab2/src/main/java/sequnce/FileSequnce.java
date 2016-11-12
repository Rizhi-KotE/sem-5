package sequnce;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.IntToDoubleFunction;

public class FileSequnce implements IntToDoubleFunction {
    private final File file;
    private Scanner scanner;

    public FileSequnce(File file) {
        this.file = file;
        try {
            scanner = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double applyAsDouble(int value) {
        String next = scanner.next();
        return Double.valueOf(next);
    }
}
