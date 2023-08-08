package by.it_academy.audit_service.config;

import by.it_academy.audit_service.config.property.JWTProperty;
import by.it_academy.audit_service.dao.entity.Audit;
import by.it_academy.audit_service.endpoints.web.support.converters.GenericAuditDtoConverter;
import by.it_academy.audit_service.service.support.spring.converters.GenericAuditConverter;
import by.it_academy.audit_service.utils.JwtTokenHandler;
import by.it_academy.task_manager_common.support.spring.converters.CustomPageConverter;
import by.it_academy.task_manager_common.support.spring.converters.GenericUserDetailsConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class ApplicationConfig implements WebMvcConfigurer {


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new GenericAuditDtoConverter());
        registry.addConverter(new CustomPageConverter<Audit>());
        registry.addConverter(new GenericAuditConverter(jwtTokenHandler(jwtProperty())));
        registry.addConverter(new GenericUserDetailsConverter());
    }

    @Bean
    public JwtTokenHandler jwtTokenHandler(JWTProperty jwtProperty) {
        return new JwtTokenHandler(jwtProperty);
    }

    @Bean
    @ConfigurationProperties("jwt")
    public JWTProperty jwtProperty() {
        return new JWTProperty();
    }


}
