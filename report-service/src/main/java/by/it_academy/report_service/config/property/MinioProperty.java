package by.it_academy.report_service.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("minio")
public class MinioProperty {

    private String url;

    private Credentials credentials;

    @Getter
    @Setter
    public static class Credentials {
        private String user;
        private String password;
    }

}
