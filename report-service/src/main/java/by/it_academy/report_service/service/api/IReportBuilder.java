package by.it_academy.report_service.service.api;

import by.it_academy.report_service.dao.entity.Report;

public interface IReportBuilder {

    byte[] build(Report report);

}
