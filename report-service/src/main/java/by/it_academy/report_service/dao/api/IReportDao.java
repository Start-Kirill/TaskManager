package by.it_academy.report_service.dao.api;

import by.it_academy.report_service.core.enums.ReportStatus;
import by.it_academy.report_service.dao.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.List;
import java.util.UUID;


public interface IReportDao extends
        JpaRepository<Report, UUID>,
        ListPagingAndSortingRepository<Report, UUID> {

    List<Report> findAllByStatus(ReportStatus status);
}
