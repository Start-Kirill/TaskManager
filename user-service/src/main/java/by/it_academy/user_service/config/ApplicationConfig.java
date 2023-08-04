package by.it_academy.user_service.config;

import by.it_academy.task_manager_common.support.spring.converters.CustomPageConverter;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.endpoints.web.support.converters.GenericUserDtoConverter;
import by.it_academy.user_service.endpoints.web.support.converters.LocalDateTimeToMilliFormatter;
import by.it_academy.user_service.service.support.converters.GenericUserConverter;
import by.it_academy.user_service.service.support.converters.GenericUserCreateDtoConverter;
import by.it_academy.user_service.service.support.converters.GenericVerificationCodeConverter;
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
        registry.addConverter(new CustomPageConverter<User>());
        registry.addConverter(new GenericVerificationCodeConverter());
    }

    @Bean
    public LocalDateTimeToMilliFormatter localDateTimeToMilliConverter() {
        return new LocalDateTimeToMilliFormatter();
    }

//    @Bean
//    public IAuditClient auditClient(AppProperty property) {
//        return Feign.builder()
//                .client(new OkHttpClient())
//                .encoder(new GsonEncoder())
//                .decoder(new GsonDecoder())
//                .logger(new Slf4jLogger(IAuditClient.class))
//                .logLevel(Logger.Level.FULL)
//                .target(IAuditClient.class, property.getAudit().getUrl());
//    }
}
