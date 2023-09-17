package by.it_academy;

import by.it_academy.report_service.config.property.JWTProperty;
import by.it_academy.report_service.config.property.MinioProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableConfigurationProperties({JWTProperty.class, MinioProperty.class})
@EnableFeignClients
@EnableJpaRepositories
@SpringBootApplication
public class ReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportServiceApplication.class, args);
    }

}
