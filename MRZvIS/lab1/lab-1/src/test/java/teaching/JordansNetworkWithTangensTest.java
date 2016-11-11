package teaching;

import com.fasterxml.jackson.databind.ObjectMapper;
import networks.JordansNetworkWithTangens;
import org.junit.Before;
import org.junit.Test;

public class JordansNetworkWithTangensTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void save() throws Exception {
        JordansNetworkWithTangens jordansNetworkWithTangens = new JordansNetworkWithTangens(3, 3);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(jordansNetworkWithTangens));
    }

    @Test
    public void load() throws Exception {
        JordansNetworkWithTangens jordansNetworkWithTangens = new JordansNetworkWithTangens(3, 3);
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(jordansNetworkWithTangens);
        mapper.readValue(s, JordansNetworkWithTangens.class);
    }

}