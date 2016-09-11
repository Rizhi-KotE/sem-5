import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MatrixTest {
    double[][] array = {{1., 2.}, {3., 4.}};
    double[] result = {1., 2., 3., 4.};

    @Test
    public void to1DVectorTest(){
        List list = Arrays.asList(1, 2, 3, 4, 5);
        double[] y = Arrays.stream(array).flatMapToDouble(Arrays::stream).toArray();
        assertArrayEquals(y, result, 0.000);
    }
}
