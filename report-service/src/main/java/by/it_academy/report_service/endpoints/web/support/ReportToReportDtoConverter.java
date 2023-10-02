package by.it_academy.report_service.endpoints.web.support;

import by.it_academy.report_service.core.dto.ReportDto;
import by.it_academy.report_service.core.enums.ReportStatus;
import by.it_academy.report_service.core.enums.ReportType;
import by.it_academy.report_service.dao.entity.Report;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class ReportToReportDtoConverter implements Converter<Report, ReportDto> {
    @Override
    public ReportDto convert(Report source) {

        UUID uuid = source.getUuid();
        LocalDateTime dtCreate = source.getDtCreate();
        LocalDateTime dtUpdate = source.getDtUpdate();
        ReportType type = source.getType();
        ReportStatus status = source.getStatus();
        String description = source.getDescription();
        Map<String, String> params = source.getParams();

        ReportDto reportDto = new ReportDto(uuid, dtCreate, dtUpdate, status, type, description, params);

        return reportDto;
    }
}
