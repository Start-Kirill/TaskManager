package by.it_academy.user_service.config;

import by.it_academy.user_service.service.support.converters.GenericMillisecondConverter;
import by.it_academy.user_service.service.support.converters.GenericUserConverter;
import by.it_academy.user_service.service.support.converters.GenericUserCreateDtoConverter;
import by.it_academy.user_service.service.support.converters.GenericUserDtoConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new GenericUserConverter());
        registry.addConverter(new GenericMillisecondConverter());
        registry.addConverter(new GenericUserDtoConverter());
        registry.addConverter(new GenericUserCreateDtoConverter());
    }
}
