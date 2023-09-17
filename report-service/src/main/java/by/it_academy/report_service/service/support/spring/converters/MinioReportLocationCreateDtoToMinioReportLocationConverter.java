package by.it_academy.report_service.service.support.spring.converters;

import by.it_academy.report_service.core.dto.MinioReportLocationCreateDto;
import by.it_academy.report_service.dao.entity.MinioReportLocation;
import org.springframework.core.convert.converter.Converter;

public class MinioReportLocationCreateDtoToMinioReportLocationConverter implements Converter<MinioReportLocationCreateDto, MinioReportLocation> {
    @Override
    public MinioReportLocation convert(MinioReportLocationCreateDto source) {
        MinioReportLocation minioReportLocation = new MinioReportLocation();
        minioReportLocation.setFileName(source.getFileName());
        minioReportLocation.setBucketName(source.getBucketName());
        return minioReportLocation;
    }
}
