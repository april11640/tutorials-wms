package tu.wms.framework.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.springframework.boot.jackson.JsonComponent;
import tu.wms.framework.model.CodeEnum;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by zsp on 2018/8/14.
 */
@JsonComponent
public class CodeEnumJsonDeserializer<E extends Enum<E>> extends JsonDeserializer<E> implements ContextualDeserializer {

    private Class<E> clazz;

    public CodeEnumJsonDeserializer() {
    }

    public CodeEnumJsonDeserializer(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public E deserialize(JsonParser p, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
        JsonToken currJsonToken = p.getCurrentToken();
        if (JsonToken.VALUE_NULL.equals(currJsonToken)) {
            return null;
        }
        int code = p.getIntValue();
        E[] enumConstants = clazz.getEnumConstants();
        for(E item : enumConstants) {
            if(Objects.equals(code, ((CodeEnum)item).getCode())) {
                return item;
            }
        }

        return null;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext,
                                                BeanProperty beanProperty) throws JsonMappingException {
        Class<E> clazz = (Class<E>) deserializationContext.getContextualType().getRawClass();
        return new CodeEnumJsonDeserializer<>(clazz);
    }
}
