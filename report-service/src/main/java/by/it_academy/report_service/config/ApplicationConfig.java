package by.it_academy.report_service.config;


import by.it_academy.report_service.dao.entity.Report;
import by.it_academy.report_service.service.support.spring.converters.ReportCreateDtoToReportConverter;
import by.it_academy.task_manager_common.support.spring.converters.PageToCustomPageConverter;
import by.it_academy.task_manager_common.support.spring.converters.UserDtoToUserDetailsConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserDtoToUserDetailsConverter());
        registry.addConverter(new ReportCreateDtoToReportConverter());
        registry.addConverter(new PageToCustomPageConverter<Report>());
    }
}
