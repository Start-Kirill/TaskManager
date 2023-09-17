package by.it_academy.report_service.dao.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "minio_report_location")
public class MinioReportLocation {

    @Id
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "bucket_name")
    private String bucketName;

    public MinioReportLocation() {
    }

    public MinioReportLocation(UUID uuid, Report report, String fileName, String bucketName) {
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

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
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
