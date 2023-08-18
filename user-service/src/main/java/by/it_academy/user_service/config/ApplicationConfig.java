package by.it_academy.user_service.config;

import by.it_academy.task_manager_common.support.spring.converters.UserDtoToUserDetailsConverter;
import by.it_academy.task_manager_common.support.spring.converters.PageToCustomPageConverter;
import by.it_academy.task_manager_common.support.spring.formatters.MilliToLocalDateTimeFormatter;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.endpoints.web.support.spring.converters.UserToUserDtoConverter;
import by.it_academy.user_service.core.support.spring.converters.UserToUserDetailsConverter;
import by.it_academy.user_service.service.support.spring.converters.UserCreateDtoToUserConverter;
import by.it_academy.user_service.service.support.spring.converters.GenericUserCreateDtoConverter;
import by.it_academy.user_service.service.support.spring.converters.VerificationCreateDtoToVerificationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class ApplicationConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserCreateDtoToUserConverter());
        registry.addFormatterForFieldType(LocalDateTime.class, localDateTimeToMilliConverter());
        registry.addConverter(new UserToUserDtoConverter());
        registry.addConverter(new GenericUserCreateDtoConverter());
        registry.addConverter(new PageToCustomPageConverter<User>());
        registry.addConverter(new VerificationCreateDtoToVerificationConverter());
        registry.addConverter(new UserDtoToUserDetailsConverter());
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

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

}
