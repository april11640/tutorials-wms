package tu.wms.uc.configuration;

//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
public class SessionConfiguration {

//    private final RedisConnectionFactory redisConnectionFactory;
//
//    public SessionConfiguration(ObjectProvider<RedisConnectionFactory> redisConnectionFactory) {
//        this.redisConnectionFactory = redisConnectionFactory.getIfAvailable();
//    }
//
//    @Bean
//    public RedisOperations<Object, Object> sessionRedisOperations() {
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(this.redisConnectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        return redisTemplate;
//    }
//
//    @Bean
//    public RedisOperationsSessionRepository sessionRepository(
//            RedisOperations<Object, Object> sessionRedisOperations) {
//        return new RedisOperationsSessionRepository(sessionRedisOperations);
//    }

}
