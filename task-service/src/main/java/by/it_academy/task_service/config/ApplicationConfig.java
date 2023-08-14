package by.it_academy.task_service.config;

import by.it_academy.task_manager_common.support.spring.converters.GenericUserDetailsConverter;
import by.it_academy.task_manager_common.support.spring.converters.PageToCustomPageConverter;
import by.it_academy.task_manager_common.support.spring.formatters.MilliToLocalDateTimeFormatter;
import by.it_academy.task_service.core.enums.TaskStatus;
import by.it_academy.task_service.dao.entity.Project;
import by.it_academy.task_service.endpoints.web.support.spring.converters.GenericProjectDtoConverter;
import by.it_academy.task_service.endpoints.web.support.spring.converters.GenericTaskDtoConverter;
import by.it_academy.task_service.endpoints.web.support.spring.formatters.StringToTaskStatusFormatter;
import by.it_academy.task_service.service.support.spring.converters.GenericProjectConverter;
import by.it_academy.task_service.service.support.spring.converters.GenericTaskConverter;
import feign.Feign;
import feign.httpclient.ApacheHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new GenericProjectDtoConverter());
        registry.addFormatterForFieldType(LocalDateTime.class, new MilliToLocalDateTimeFormatter());
        registry.addConverter(new GenericTaskDtoConverter());
        registry.addFormatterForFieldType(TaskStatus.class, new StringToTaskStatusFormatter());
        registry.addConverter(new GenericProjectConverter());
        registry.addConverter(new PageToCustomPageConverter<Project>());
        registry.addConverter(new GenericUserDetailsConverter());
        registry.addConverter(new GenericTaskConverter());
    }

    @Bean
    public Feign.Builder feign() {
        return Feign.builder().client(new ApacheHttpClient());
    }
}
