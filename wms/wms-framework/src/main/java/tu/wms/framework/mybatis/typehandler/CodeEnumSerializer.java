package tu.wms.framework.mybatis.typehandler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import tu.wms.framework.model.CodeEnum;

import java.io.IOException;

public class CodeEnumSerializer<E extends Enum<E>> extends JsonSerializer<E> implements ContextualSerializer {

	private Class<E> clazz;

	public CodeEnumSerializer() {

	}

	public CodeEnumSerializer(Class<E> clazz) {
		this.clazz = clazz;
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
		Class<E> clazz = (Class<E>) beanProperty.getType().getRawClass();
		return new CodeEnumSerializer<>(clazz);
	}

	@Override
	public void serialize(E value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		if (value == null) {
			jgen.writeNull();
		} else if (CodeEnum.class.isAssignableFrom(clazz)){
			jgen.writeNumber(((CodeEnum) value).getCode());
		} else {
			jgen.writeString(value.name());
		}
	}
	
}
