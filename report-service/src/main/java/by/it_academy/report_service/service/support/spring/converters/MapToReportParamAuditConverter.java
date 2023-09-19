package by.it_academy.report_service.service.support.spring.converters;

import by.it_academy.report_service.core.dto.ReportParamAudit;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class MapToReportParamAuditConverter implements Converter<Map<String, Object>, ReportParamAudit> {

    private static final String REPORT_AUDIT_USER_PARAM_NAME = "user";

    private static final String REPORT_AUDIT_FROM_PARAM_NAME = "from";

    private static final String REPORT_AUDIT_TO_PARAM_NAME = "to";


    @Override
    public ReportParamAudit convert(Map<String, Object> source) {

        UUID user = (UUID) source.get("user");
        LocalDateTime from = (LocalDateTime) source.get("from");
        LocalDateTime to = (LocalDateTime) source.get("to");

        return new ReportParamAudit(user, from, to);
    }
}
