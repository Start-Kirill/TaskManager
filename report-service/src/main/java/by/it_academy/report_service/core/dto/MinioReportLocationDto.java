package by.it_academy.report_service.core.dto;

import java.util.UUID;

public class MinioReportLocationDto {

    private UUID uuid;


    private UUID report;


    private String fileName;


    private String bucketName;

    public MinioReportLocationDto() {
    }

    public MinioReportLocationDto(UUID uuid, UUID report, String fileName, String bucketName) {
        this.uuid = uuid;
        this.report = report;
        this.fileName = fileName;
        this.bucketName = bucketName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
