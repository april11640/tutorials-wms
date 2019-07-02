package tu.wms.purchasing.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"tu.wms.framework",
        "tu.wms.purchasing"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"tu.wms.inventorying.facade.api"})
@MapperScan(basePackages = {"tu.wms.purchasing.dao.api"})
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
