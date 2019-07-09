package tu.wms.framework.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;

public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper defaultMapper = new ObjectMapper();

    public void setObjectMapper(ObjectMapper mapper) {
        defaultMapper = mapper;
    }

    /**
     * 序列化成字符串
     */
    public static String toJson(Object value) {
        return toJson(defaultMapper, value);
    }

    /**
     * 序列化成字符串
     */
    public static String toJson(final ObjectMapper mapper, Object value) {
        if (value == null) {
            return null;
        }
        ObjectMapper _mapper = mapper;
        if (_mapper == null) {
            _mapper = defaultMapper;
        }
        try {
            return _mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            logger.error("序列化错误: value=" + value, e);
        }
        return null;
    }

    /**
     * 根据类型反序列化为对象
     */
    public static <T> T parse(String value, Class<T> clasz) {
        return parse(defaultMapper, value, clasz);
    }

    /**
     * 根据类型反序列化为对象
     */
    public static <T> T parse(final ObjectMapper mapper, String value, Class<T> clasz) {
        if (clasz == null) {
            throw new IllegalArgumentException("clasz is null");
        }
        if (value == null || "".equals(value.trim())) {
            return null;
        }
        ObjectMapper _mapper = mapper;
        if (_mapper == null) {
            _mapper = defaultMapper;
        }
        try {
            return _mapper.readValue(value, clasz);
        } catch (IOException e) {
            logger.error("反序列化错误: class=" + clasz + ", value=" + value, e);
        }
        return null;
    }


    /// tests

    private static class Parent {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    private static class Child extends Parent {
        private Long count;

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }
    }

    private static class SParent implements Serializable {
        private String value;

        public SParent() {

        }

        public SParent(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    private static class SChild extends SParent {
        private Long count;

        public SChild() {
            super();
        }

        public SChild(String value) {
            super(value);
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }
    }

    public static void main(String[] args) {
        Parent parent = new Parent();
        parent.setValue("parent");
        Child child = new Child();
        child.setValue("child");
        child.setCount(10L);
        SParent sParent = new SParent("sparent");
        SChild sChild = new SChild("schild");
        sChild.setCount(20L);

        String parentString = JsonUtils.toJson(parent);
        String childString = JsonUtils.toJson(child);
        String sParentString = JsonUtils.toJson(sParent);
        String sChildString = JsonUtils.toJson(sChild);

        JsonUtils.parse(parentString, Parent.class);
        JsonUtils.parse(childString, Child.class);
        JsonUtils.parse(sParentString, SParent.class);
        JsonUtils.parse(sChildString, SChild.class);
    }

}
