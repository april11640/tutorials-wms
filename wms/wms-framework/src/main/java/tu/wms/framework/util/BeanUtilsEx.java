package tu.wms.framework.util;

import org.springframework.beans.BeanUtils;

import java.util.Objects;
import java.util.function.Supplier;

/**
 *
 */
public class BeanUtilsEx {

    public final static <S, T> void copyProperties(S source, T target) {
        copyProperties(source, target, true);
    }

    public final static <S, T> void copyProperties(S source, T target, boolean require) {
        if(require) {
            Objects.requireNonNull(source);
            Objects.requireNonNull(target);
        }
        BeanUtils.copyProperties(source, target);
    }

    public final static <S, T> T copyProperties(S source, Supplier<T> factory) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(factory);
        T target = factory.get();
        Objects.requireNonNull(target);
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public final static <S, T> T copyProperties(S source, Supplier<T> factory, boolean require) {
        if(require) {
            Objects.requireNonNull(source);
            Objects.requireNonNull(factory);
        } else if(source == null) {
            return null;
        }
        T target = factory.get();
        if(require) {
            Objects.requireNonNull(target);
        } else if(target == null) {
            return null;
        }
        BeanUtils.copyProperties(source, target);
        return target;
    }

}
