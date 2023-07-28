package by.it_academy.audit_service.config;

import by.it_academy.audit_service.endpoints.web.support.converters.GenericAuditDtoConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class ApplicationConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new GenericAuditDtoConverter());
    }

}
