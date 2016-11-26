import org.junit.Test;
import sequnce.FibonachiSequence;

import java.util.stream.IntStream;

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

    @Test
    public void fibonachiSequence() throws Exception {
        FibonachiSequence fibonachi = new FibonachiSequence(100);
        double[] doubles = fibonachi.getSequence().toArray();
        double a = new FibonachiSequence(100).getSequence().reduce(0, (left, right) -> left += right < Integer.MAX_VALUE ? 1 : 0);
        String substring = Integer.toBinaryString((int) doubles[20]);
        StringBuilder builder = new StringBuilder();
        for (int i = substring.length(); i < 16; i++) builder.append("0");
        builder.append(substring);
        String substring1 = builder.toString();
        double number[] = new double[16];
        for (int i = 0; i < substring.length(); i++) {
            number[i] = substring.charAt(i) == '1' ? 1 : 0;
        }
    }
}
