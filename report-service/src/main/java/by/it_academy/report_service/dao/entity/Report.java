package by.it_academy.report_service.dao.entity;

import by.it_academy.report_service.core.enums.ReportStatus;
import by.it_academy.report_service.core.enums.ReportType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
public class Report {

    @Id
    private UUID uuid;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    @Enumerated(EnumType.STRING)
    private ReportType type;

    @Column(updatable = false)
    private String description;

    @ElementCollection
    @CollectionTable(name = "report_param_audit", joinColumns = @JoinColumn(name = "report_id"))
    private Map<String, Object> auditParams;

    private Integer attempt;

    public Report() {
    }

    public Report(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, ReportStatus status, ReportType type, String description, Map<String, Object> auditParams, Integer attempt) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.status = status;
        this.type = type;
        this.description = description;
        this.auditParams = auditParams;
        this.attempt = attempt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getAuditParams() {
        return auditParams;
    }

    public void setAuditParams(Map<String, Object> auditParams) {
        this.auditParams = auditParams;
    }

    public Integer getAttempt() {
        return attempt;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }
}