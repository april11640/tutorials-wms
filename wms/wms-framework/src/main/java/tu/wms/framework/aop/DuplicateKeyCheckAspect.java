package tu.wms.framework.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.dao.DuplicateKeyException;
import tu.wms.framework.model.Result;

import java.lang.reflect.Method;

import static tu.wms.framework.model.CommonConstants.DUPLICATE_KEY_AFFECTED_ROWS;

@Aspect
public class DuplicateKeyCheckAspect {

    @Pointcut("@annotation(tu.wms.framework.aop.DuplicateKeyCheck)")
    public void check() {

    }

    @Around("check()")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (DuplicateKeyException e) {
            Method method = ((MethodSignature) pjp.getSignature()).getMethod();
            Class<?> returnType = method.getReturnType();
            if(Void.TYPE.equals(returnType) || method.getAnnotation(DuplicateKeyCheck.class).forceThrowException()) {
                throw e;
            }
            if(Number.class.isAssignableFrom(returnType)
                    || int.class.isAssignableFrom(returnType)
                    || long.class.isAssignableFrom(returnType)) {
                return DUPLICATE_KEY_AFFECTED_ROWS;
            }
            if(Result.class.isAssignableFrom(returnType)) {
                return Result.conflict();
            }
            throw e;
        }
    }

}
