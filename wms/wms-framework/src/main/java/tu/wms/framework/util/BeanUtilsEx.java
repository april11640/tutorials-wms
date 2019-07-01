package tu.wms.framework.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
        return copyProperties(source, factory, true);
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

    public final static <S, T> List<T> copyProperties(Collection<S> source, Supplier<T> factory) {
        return copyProperties(source, factory, true);
    }

    public final static <S, T> List<T> copyProperties(Collection<S> source, Supplier<T> factory, boolean require) {
        List<T> target = new ArrayList<>();
        if(require) {
            if(CollectionUtils.isEmpty(source)) {
                throw new IllegalArgumentException();
            }
            Objects.requireNonNull(factory);
        } else if(CollectionUtils.isEmpty(source)) {
            return target;
        }
        for(S sourceItem : source) {
            T targetItem = factory.get();
            if (require) {
                Objects.requireNonNull(targetItem);
            } else if (targetItem == null) {
               continue;
            }
            BeanUtils.copyProperties(sourceItem, targetItem);
            target.add(targetItem);
        }
        return target;
    }

}
