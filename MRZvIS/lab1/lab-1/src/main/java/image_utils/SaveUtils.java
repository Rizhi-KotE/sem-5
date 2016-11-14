package image_utils;

import Jama.Matrix;
import com.fasterxml.jackson.databind.ObjectMapper;
import teaching.NeuronsNetwork;

import java.io.File;
import java.io.IOException;

public class SaveUtils {
    public static void saveNetwork(File file, NeuronsNetwork network) {
        double[][][] matrices = {network.getW().getArray(), network.getWH().getArray()};
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, matrices);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePlot(File file, double[][] list) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, list);
    }

    public static void saveObject(File file, Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}