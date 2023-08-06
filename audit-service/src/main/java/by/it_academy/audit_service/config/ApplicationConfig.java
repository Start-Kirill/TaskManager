package by.it_academy.audit_service.config;

import by.it_academy.audit_service.dao.entity.Audit;
import by.it_academy.audit_service.endpoints.web.support.converters.GenericAuditDtoConverter;
import by.it_academy.audit_service.service.support.spring.converters.GenericAuditConverter;
import by.it_academy.audit_service.utils.JwtTokenHandler;
import by.it_academy.task_manager_common.support.spring.converters.CustomPageConverter;
import by.it_academy.task_manager_common.support.spring.converters.GenericUserDetailsConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Import({JwtTokenHandler.class})
public class ApplicationConfig implements WebMvcConfigurer {

    private final JwtTokenHandler tokenHandler;

    public ApplicationConfig(@Lazy JwtTokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new GenericAuditDtoConverter());
        registry.addConverter(new CustomPageConverter<Audit>());
        registry.addConverter(new GenericAuditConverter(tokenHandler));
        registry.addConverter(new GenericUserDetailsConverter());
    }

}
