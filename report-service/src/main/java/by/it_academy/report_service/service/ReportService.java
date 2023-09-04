package by.it_academy.report_service.service;

import by.it_academy.report_service.core.dto.ReportCreateDto;
import by.it_academy.report_service.core.dto.ReportDto;
import by.it_academy.report_service.dao.api.IReportDao;
import by.it_academy.report_service.service.api.IReportService;
import by.it_academy.task_manager_common.dto.CustomPage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReportService implements IReportService {

    private final IReportDao reportDao;

    private final UserHolder userHolder;

    public ReportService(IReportDao reportDao, UserHolder userHolder) {
        this.reportDao = reportDao;
        this.userHolder = userHolder;
    }

    @Override
    public ReportDto save(ReportCreateDto reportCreateDto) {
        return null;
    }

    @Override
    public ReportDto update(ReportCreateDto reportCreateDto, UUID uuid, LocalDateTime dtUpdate) {
        return null;
    }

    @Override
    public ReportDto get(UUID uuid) {
        return null;
    }

    @Override
    public CustomPage<ReportDto> get(Integer page, Integer size) {
        return null;
    }
}
