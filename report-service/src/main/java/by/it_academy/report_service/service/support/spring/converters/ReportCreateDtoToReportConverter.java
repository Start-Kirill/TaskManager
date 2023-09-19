package by.it_academy.report_service.service.support.spring.converters;

import by.it_academy.report_service.core.dto.ReportCreateDto;
import by.it_academy.report_service.core.enums.ReportStatus;
import by.it_academy.report_service.core.enums.ReportType;
import by.it_academy.report_service.dao.entity.Report;
import org.springframework.core.convert.converter.Converter;

public class ReportCreateDtoToReportConverter implements Converter<ReportCreateDto, Report> {

    @Override
    public Report convert(ReportCreateDto source) {
        Report report = new Report();
        report.setAttempt(0);
        report.setParams(source.getParams());
        report.setStatus(ReportStatus.LOADED);
        report.setType(ReportType.JOURNAL_AUDIT);
        report.setDescription(ReportType.JOURNAL_AUDIT.buildDescription(source.getParams()));
        return report;
    }


}
