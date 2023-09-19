package by.it_academy.report_service.service.support.spring.converters;

import by.it_academy.task_manager_common.dto.ReportParamAudit;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

public class MapToReportParamAuditConverter implements Converter<Map<String, String>, ReportParamAudit> {

    private final static String USER_FIELD_PARAM_NAME = "user";

    private final static String FROM_FIELD_PARAM_NAME = "from";

    private final static String TO_FIELD_PARAM_NAME = "to";

    private final static String pattern = "yyyy-MM-dd";


    @Override
    public ReportParamAudit convert(Map<String, String> source) {

        String user = source.get(USER_FIELD_PARAM_NAME);
        UUID uuid = UUID.fromString(user);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        String fromRaw = source.get(FROM_FIELD_PARAM_NAME);
        LocalDateTime from = LocalDateTime.of(LocalDate.parse(fromRaw, formatter), LocalTime.MIN);


        String toRaw = source.get(TO_FIELD_PARAM_NAME);
        LocalDateTime to = LocalDateTime.of(LocalDate.parse(toRaw, formatter), LocalTime.MAX);


        return new ReportParamAudit(uuid, from, to);
    }
}
