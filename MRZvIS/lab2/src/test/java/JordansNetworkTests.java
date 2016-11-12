import Jama.Matrix;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
import org.junit.Test;
import sequnce.TrainingSetGenerator;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JordansNetworkTests {
//    @Test
//    public void testTeaching() throws IOException {
//        int inputVectorSize = 100;
//        ObjectMapper mapper = new ObjectMapper();
//        JordansNetworkWithTangens network = mapper.readValue(new File("/home/rizhi-kote/Student/sem-5/MRZvIS/lab2/src/main/resources/network.json"),
//                JordansNetworkWithTangens.class);
//        TrainingSetGenerator generator = new TrainingSetGenerator((x) -> 0.1);
//        Stream<Pair<Matrix, Matrix>> set = generator.createTrainingSet(1, 2);
//        for (Pair<Matrix, Matrix> pair : set.collect(Collectors.toList())) {
//            pair.getValue().set(0, 0, 0.1);
//            Matrix output = network.straightPropagation(pair.getKey());
//            Matrix delta = output.minus(pair.getValue());
//            network.backPropagate(delta, 1);
//        }
//    }
}
