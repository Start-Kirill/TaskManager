package by.it_academy.report_service.service.api;

import by.it_academy.report_service.core.dto.MinioReportLocationCreateDto;
import by.it_academy.report_service.dao.entity.MinioReportLocation;
import by.it_academy.report_service.dao.entity.Report;

import java.util.UUID;

public interface IMinioReportLocationService extends IReportLocationService<MinioReportLocation, MinioReportLocationCreateDto> {

    MinioReportLocation findByReport(UUID reportUuid);

    boolean existsByReport(UUID reportUuid);

}
