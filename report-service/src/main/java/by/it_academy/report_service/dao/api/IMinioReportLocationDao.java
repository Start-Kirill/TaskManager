package by.it_academy.report_service.dao.api;

import by.it_academy.report_service.dao.entity.MinioReportLocation;
import by.it_academy.report_service.dao.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.UUID;

public interface IMinioReportLocationDao extends
        JpaRepository<MinioReportLocation, UUID>,
        ListPagingAndSortingRepository<MinioReportLocation, UUID> {

    MinioReportLocation findByReport(Report report);
}
