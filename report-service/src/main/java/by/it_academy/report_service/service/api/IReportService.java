package by.it_academy.report_service.service.api;

import by.it_academy.report_service.core.dto.ReportCreateDto;
import by.it_academy.report_service.core.dto.ReportUpdateDto;
import by.it_academy.report_service.core.enums.ReportStatus;
import by.it_academy.report_service.dao.entity.Report;

import java.util.List;
import java.util.UUID;

public interface IReportService extends ICRUDService<ReportCreateDto, ReportUpdateDto, Report> {

    String getUrl(UUID report);

    boolean checkAvailability(UUID reportUuid);

    List<Report> getByStatus(ReportStatus status);

}
