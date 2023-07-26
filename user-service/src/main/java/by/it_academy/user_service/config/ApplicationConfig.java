package by.it_academy.user_service.config;

import by.it_academy.user_service.endpoints.web.support.converters.GenericUserDtoConverter;
import by.it_academy.user_service.endpoints.web.support.converters.LocalDateTimeToMilliFormatter;
import by.it_academy.user_service.service.support.converters.GenericUserConverter;
import by.it_academy.user_service.service.support.converters.GenericUserCreateDtoConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new GenericUserConverter());
        registry.addFormatterForFieldType(LocalDateTime.class, localDateTimeToMilliConverter());
        registry.addConverter(new GenericUserDtoConverter());
        registry.addConverter(new GenericUserCreateDtoConverter());
    }

    @Bean
    public LocalDateTimeToMilliFormatter localDateTimeToMilliConverter() {
        return new LocalDateTimeToMilliFormatter();
    }
}
