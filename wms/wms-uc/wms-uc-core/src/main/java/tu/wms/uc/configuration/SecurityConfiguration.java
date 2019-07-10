package tu.wms.uc.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration
        extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                // other config goes here...
//                .sessionManagement()
//                .maximumSessions(2)
//                .sessionRegistry(sessionRegistry());
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll();
//                    .anyRequest().authenticated()
//                    .and()
//                .formLogin()
//                    .permitAll();
    }

}
