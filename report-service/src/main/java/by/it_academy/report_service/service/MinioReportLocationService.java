package by.it_academy.report_service.service;

import by.it_academy.report_service.core.dto.MinioReportLocationCreateDto;
import by.it_academy.report_service.dao.entity.MinioReportLocation;
import by.it_academy.report_service.dao.entity.Report;
import by.it_academy.report_service.service.api.IMinioReportLocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MinioReportLocationService implements IMinioReportLocationService {


    @Override
    public MinioReportLocation save(MinioReportLocationCreateDto dto) {
        return null;
    }

    @Override
    public MinioReportLocation get(UUID uuid) {
        return null;
    }

    @Override
    public List<MinioReportLocation> get() {
        return null;
    }

    @Override
    public MinioReportLocation findByReport(UUID report) {
        return null;
    }
}
