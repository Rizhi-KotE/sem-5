

import Jama.Matrix;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.lang.reflect.Field;

public class NetworkSerializer extends StdSerializer<JordansNetworkWithTangens> {

    public NetworkSerializer() {
        this(null);
    }

    protected NetworkSerializer(Class<JordansNetworkWithTangens> t) {
        super(t);
    }

    @Override
    public void serialize(JordansNetworkWithTangens value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        Field[] declaredFields = value.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                if (field.get(value) instanceof Matrix) {
                    Matrix matrix = (Matrix) field.get(value);
                    gen.writeObjectField(field.getName(), matrix.getArray());
                } else if (field.get(value) instanceof TangensFunction) {
                } else {
                    gen.writeObjectField(field.getName(), field.get(value));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        gen.writeEndObject();
    }
}
