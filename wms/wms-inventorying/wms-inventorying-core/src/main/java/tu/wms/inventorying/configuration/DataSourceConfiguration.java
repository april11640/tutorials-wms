package tu.wms.inventorying.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
public class DataSourceConfiguration {

    @Value("${mybatis.mapperLocations}")
    private String mapperLocations;

    @Value("${mybatis.config-location}")
    private String configLocation;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        return datasource;
    }

    @Primary
    @Bean
    public DataSourceProxy dataSourceProxy(DruidDataSource druidDataSource){
        return new DataSourceProxy(druidDataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSourceProxy dataSourceProxy) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSourceProxy);
        bean.setTransactionFactory(new JdbcTransactionFactory());
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setConfigLocation(resolver.getResource(configLocation));
            bean.setMapperLocations(resolver.getResources(mapperLocations));

            return bean.getObject();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
