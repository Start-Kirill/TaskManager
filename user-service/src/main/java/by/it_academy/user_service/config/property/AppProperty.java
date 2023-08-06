package by.it_academy.user_service.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("app")
public class AppProperty {

    private Verification verification;


    @Getter
    @Setter
    public static class Verification {
        private String url;
    }

}
