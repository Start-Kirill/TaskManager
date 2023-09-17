package by.it_academy.report_service.service;

import by.it_academy.report_service.core.dto.ReportUpdateDto;
import by.it_academy.report_service.core.enums.ReportStatus;
import by.it_academy.report_service.core.enums.ReportType;
import by.it_academy.report_service.dao.entity.Report;
import by.it_academy.report_service.service.api.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MinioReportScheduler implements IReportScheduler {

    private final static String FORMAT_FILE_POSTFIX = ".xlsx";

    private final IReportService reportService;

    private final IMinioService minioService;

    private final IReportBuilder reportBuilder;


    public MinioReportScheduler(IReportService reportService,
                                IMinioService minioService,
                                IReportBuilder reportBuilder) {
        this.reportService = reportService;
        this.minioService = minioService;
        this.reportBuilder = reportBuilder;
    }

    @Scheduled(fixedDelay = 10000)
    @Override
    public void execute() {
        List<Report> loadedReports = this.reportService.getByStatus(ReportStatus.LOADED);
        ReportUpdateDto reportUpdateDto = new ReportUpdateDto();
        loadedReports.forEach(r -> {
            try {
                reportUpdateDto.setStatus(ReportStatus.PROGRESS);
                reportUpdateDto.setAttempt(r.getAttempt());

                r = this.reportService.update(reportUpdateDto, r.getUuid(), r.getDtUpdate());

                String reportFile = this.reportBuilder.build(r);

                this.minioService.save(reportFile, generateFileName(r), generateBucketName(r));

                reportUpdateDto.setStatus(ReportStatus.DONE);
                this.reportService.update(reportUpdateDto, r.getUuid(), r.getDtUpdate());
            } catch (Exception ex) {
                if (r.getAttempt().equals(2)) {
                    reportUpdateDto.setStatus(ReportStatus.ERROR);
                    reportUpdateDto.setAttempt(r.getAttempt());
                } else {
                    reportUpdateDto.setStatus(ReportStatus.LOADED);
                    reportUpdateDto.setAttempt(r.getAttempt() + 1);
                }
                this.reportService.update(reportUpdateDto, r.getUuid(), r.getDtUpdate());
            }
        });

    }

    private String generateFileName(Report report) {
        UUID uuid = report.getUuid();
        return uuid.toString() + FORMAT_FILE_POSTFIX;
    }

    private String generateBucketName(Report report) {
        ReportType type = report.getType();
        return type.toString().replaceAll("_", "-").toLowerCase();
    }
}
