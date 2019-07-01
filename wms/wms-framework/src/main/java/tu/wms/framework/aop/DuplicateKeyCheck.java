package tu.wms.framework.aop;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 */
@Target({ElementType.METHOD})
@Retention(RUNTIME)
public @interface DuplicateKeyCheck {

    boolean forceThrowException() default false;

}
