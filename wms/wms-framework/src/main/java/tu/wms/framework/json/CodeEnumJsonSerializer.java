package tu.wms.framework.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import tu.wms.framework.model.CodeEnum;

import java.io.IOException;

/**
 * Created by zsp on 2018/8/14.
 */
@JsonComponent
public class CodeEnumJsonSerializer<E extends Enum<E>> extends JsonSerializer<CodeEnum<E>> {
    @Override
    public void serialize(CodeEnum<E> value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeNumber(value.getCode());
    }
}
