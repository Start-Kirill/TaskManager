package by.it_academy.user_service.config;

import by.it_academy.task_manager_common.support.spring.converters.PageToCustomPageConverter;
import by.it_academy.task_manager_common.support.spring.converters.GenericUserDetailsConverter;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.endpoints.web.support.spring.converters.GenericUserDtoConverter;
import by.it_academy.task_manager_common.support.spring.formatters.MilliToLocalDateTimeFormatter;
import by.it_academy.user_service.endpoints.web.support.spring.converters.UserToUserDetailsConverter;
import by.it_academy.user_service.service.support.converters.GenericUserConverter;
import by.it_academy.user_service.service.support.converters.GenericUserCreateDtoConverter;
import by.it_academy.user_service.service.support.converters.GenericVerificationCodeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        registry.addConverter(new PageToCustomPageConverter<User>());
        registry.addConverter(new GenericVerificationCodeConverter());
        registry.addConverter(new GenericUserDetailsConverter());
        registry.addConverter(new UserToUserDetailsConverter());
    }

    @Bean
    public MilliToLocalDateTimeFormatter localDateTimeToMilliConverter() {
        return new MilliToLocalDateTimeFormatter();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
