package by.it_academy.audit_service.config;

import by.it_academy.audit_service.config.property.JWTProperty;
import by.it_academy.audit_service.dao.entity.Audit;
import by.it_academy.audit_service.endpoints.web.support.spring.converters.AuditToAuditDtoConverter;
import by.it_academy.audit_service.service.support.spring.converters.AuditCreateDtoToAuditConverter;
import by.it_academy.audit_service.utils.JwtTokenHandler;
import by.it_academy.task_manager_common.support.spring.converters.PageToCustomPageConverter;
import by.it_academy.task_manager_common.support.spring.converters.UserDtoToUserDetailsConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class ApplicationConfig implements WebMvcConfigurer {


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new AuditToAuditDtoConverter());
        registry.addConverter(new PageToCustomPageConverter<Audit>());
        registry.addConverter(new AuditCreateDtoToAuditConverter(jwtTokenHandler(jwtProperty())));
        registry.addConverter(new UserDtoToUserDetailsConverter());
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
