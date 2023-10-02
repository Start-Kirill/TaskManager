package by.it_academy.report_service.core.dto;

import java.util.UUID;

public class MinioReportLocationCreateDto {

    private UUID report;


    private String fileName;


    private String bucketName;

    public MinioReportLocationCreateDto() {
    }

    public MinioReportLocationCreateDto(UUID report, String fileName, String bucketName) {
        this.report = report;
        this.fileName = fileName;
        this.bucketName = bucketName;
    }

    public UUID getReport() {
        return report;
    }

    public void setReport(UUID report) {
        this.report = report;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
