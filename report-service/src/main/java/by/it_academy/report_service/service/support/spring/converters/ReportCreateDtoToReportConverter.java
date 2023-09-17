package by.it_academy.report_service.service.support.spring.converters;

import by.it_academy.report_service.core.dto.ReportCreateDto;
import by.it_academy.report_service.core.enums.ReportStatus;
import by.it_academy.report_service.core.enums.ReportType;
import by.it_academy.report_service.dao.entity.Report;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReportCreateDtoToReportConverter implements Converter<ReportCreateDto, Report> {

    private final static String USER_FIELD_PARAM_NAME = "user";

    private final static String FROM_FIELD_PARAM_NAME = "from";

    private final static String TO_FIELD_PARAM_NAME = "to";

    private final static String pattern = "yyyy-MM-dd";


    @Override
    public Report convert(ReportCreateDto source) {
        Report report = new Report();
        report.setAttempt(0);
        report.setAuditParams(convert(source.getParams(), source.getType()));
        report.setStatus(ReportStatus.LOADED);
        report.setType(ReportType.JOURNAL_AUDIT);
        report.setDescription(ReportType.JOURNAL_AUDIT.buildDescription(source.getParams()));
        return report;
    }

    private Map<String, Object> convert(Map<String, String> source, ReportType type) {
        Map<String, Object> target = new HashMap<>();

        if (type.equals(ReportType.JOURNAL_AUDIT)) {
            String user = source.get(USER_FIELD_PARAM_NAME);
            UUID uuid = UUID.fromString(user);
            target.put(USER_FIELD_PARAM_NAME, uuid);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            String fromRaw = source.get(FROM_FIELD_PARAM_NAME);
            LocalDateTime from = LocalDateTime.parse(fromRaw, formatter);
            target.put(FROM_FIELD_PARAM_NAME, from);

            String toRaw = source.get(TO_FIELD_PARAM_NAME);
            LocalDateTime to = LocalDateTime.parse(toRaw, formatter);
            target.put(TO_FIELD_PARAM_NAME, to);
        }

        return target;
    }

}
