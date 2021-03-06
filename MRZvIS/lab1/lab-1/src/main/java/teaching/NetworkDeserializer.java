package teaching;

import Jama.Matrix;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;

public class NetworkDeserializer extends StdDeserializer<NeuronsNetwork> {
    public NetworkDeserializer() {
        this(null);
    }

    protected NetworkDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public NeuronsNetwork deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper mapper = new ObjectMapper();
        Iterator<String> fieldNames = node.fieldNames();
        NeuronsNetwork jordansNetworkWithTangens = new NeuronsNetwork(0,0);
        Class<? extends NeuronsNetwork> clazz = jordansNetworkWithTangens.getClass();
        node.fields().forEachRemaining(stringJsonNodeEntry -> {
            try {
                Field field = clazz.getDeclaredField(stringJsonNodeEntry.getKey());
                field.setAccessible(true);
                if (stringJsonNodeEntry.getValue().isNull()) {

                } else if (stringJsonNodeEntry.getValue().isArray()) {
                    double[][] matrix = mapper.readValue(stringJsonNodeEntry.getValue().toString(), double[][].class);
                    field.set(jordansNetworkWithTangens, new Matrix(matrix));
                } else {
                    field.set(jordansNetworkWithTangens, stringJsonNodeEntry.getValue().asInt());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return jordansNetworkWithTangens;
    }
}
