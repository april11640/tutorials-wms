package tu.wms.framework.autoconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tu.wms.framework.aop.DuplicateKeyCheckAspect;

@Configuration
public class FrameworkConfiguration {

    @Bean
    public DuplicateKeyCheckAspect duplicateKeyCheckAspect() {
        return new DuplicateKeyCheckAspect();
    }

}
