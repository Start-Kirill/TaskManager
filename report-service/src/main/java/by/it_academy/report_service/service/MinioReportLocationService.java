package by.it_academy.report_service.service;

import by.it_academy.report_service.core.dto.MinioReportLocationCreateDto;
import by.it_academy.report_service.dao.api.IMinioReportLocationDao;
import by.it_academy.report_service.dao.entity.MinioReportLocation;
import by.it_academy.report_service.dao.entity.Report;
import by.it_academy.report_service.service.api.IMinioReportLocationService;
import by.it_academy.report_service.service.api.IReportService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MinioReportLocationService implements IMinioReportLocationService {

    private final IMinioReportLocationDao minioReportLocationDao;

    private final ConversionService conversionService;

    private final IReportService reportService;

    public MinioReportLocationService(IMinioReportLocationDao minioReportLocationDao, ConversionService conversionService, IReportService reportService) {
        this.minioReportLocationDao = minioReportLocationDao;
        this.conversionService = conversionService;
        this.reportService = reportService;
    }

    @Override
    public MinioReportLocation save(MinioReportLocationCreateDto dto) {

        MinioReportLocation minioReportLocation = this.conversionService.convert(dto, MinioReportLocation.class);
        minioReportLocation.setUuid(UUID.randomUUID());
        minioReportLocation.setReport(this.reportService.get(dto.getReport()));

        return this.minioReportLocationDao.save(minioReportLocation);
    }

    //    TODO handle NoSuchElementException
    @Override
    public MinioReportLocation get(UUID uuid) {
        return this.minioReportLocationDao.findById(uuid).orElseThrow();
    }

    @Override
    public List<MinioReportLocation> get() {
        return this.minioReportLocationDao.findAll();
    }


    @Override
    public MinioReportLocation findByReport(UUID reportUuid) {
        Report report = this.reportService.get(reportUuid);
        return this.minioReportLocationDao.findByReport(report).orElseThrow();
    }

    @Override
    public boolean existsByReport(UUID reportUuid) {
        Report report = this.reportService.get(reportUuid);
        return this.minioReportLocationDao.existsByReport(report);
    }

}
