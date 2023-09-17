package by.it_academy.report_service.service;

import by.it_academy.report_service.dao.api.IFileSystemDao;
import by.it_academy.report_service.dao.entity.MinioReportLocation;
import by.it_academy.report_service.service.api.IMinioReportLocationService;
import by.it_academy.report_service.service.api.IMinioService;
import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MinioService implements IMinioService {

    private final IMinioReportLocationService reportLocationService;

    private final IFileSystemDao minioDao;

    public MinioService(@Lazy IMinioReportLocationService reportLocationService, IFileSystemDao minioDao) {
        this.reportLocationService = reportLocationService;
        this.minioDao = minioDao;
    }

    @Override
    public void save(byte[] data, String fileName, String bucketName) {
        this.minioDao.save(data, fileName, bucketName);
    }

    @Override
    public String getUrl(UUID report) {
        MinioReportLocation byReport = this.reportLocationService.findByReport(report);
        String fileName = byReport.getFileName();
        String bucketName = byReport.getBucketName();
        return this.minioDao.getUrl(fileName, bucketName);
    }
}
