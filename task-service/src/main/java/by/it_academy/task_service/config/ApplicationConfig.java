package by.it_academy.task_service.config;

import by.it_academy.task_manager_common.support.spring.converters.UserDtoToUserDetailsConverter;
import by.it_academy.task_manager_common.support.spring.converters.PageToCustomPageConverter;
import by.it_academy.task_manager_common.support.spring.formatters.MilliToLocalDateTimeFormatter;
import by.it_academy.task_service.core.enums.TaskStatus;
import by.it_academy.task_service.dao.entity.Project;
import by.it_academy.task_service.endpoints.web.support.spring.converters.ProjectToProjectDtoConverter;
import by.it_academy.task_service.endpoints.web.support.spring.converters.TaskToTaskDtoConverter;
import by.it_academy.task_service.endpoints.web.support.spring.formatters.StringToTaskStatusFormatter;
import by.it_academy.task_service.service.support.spring.converters.ProjectCreateDtoToProjectConverter;
import by.it_academy.task_service.service.support.spring.converters.TaskCreateDtoToTaskConverter;
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
        registry.addConverter(new ProjectToProjectDtoConverter());
        registry.addFormatterForFieldType(LocalDateTime.class, new MilliToLocalDateTimeFormatter());
        registry.addConverter(new TaskToTaskDtoConverter());
        registry.addFormatterForFieldType(TaskStatus.class, new StringToTaskStatusFormatter());
        registry.addConverter(new ProjectCreateDtoToProjectConverter());
        registry.addConverter(new PageToCustomPageConverter<Project>());
        registry.addConverter(new UserDtoToUserDetailsConverter());
        registry.addConverter(new TaskCreateDtoToTaskConverter());
    }

    @Bean
    public Feign.Builder feign() {
        return Feign.builder().client(new ApacheHttpClient());
    }
}
