package by.it_academy.report_service.core.dto;

import by.it_academy.report_service.core.enums.ReportStatus;
import by.it_academy.report_service.core.enums.ReportType;
import by.it_academy.task_manager_common.support.json.converters.LocalDateTimeToMillisecondsConverter;
import by.it_academy.task_manager_common.support.json.converters.MillisecondsToLocalDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class ReportDto {

    private UUID uuid;

    @JsonProperty(namespace = "dt_create")
    @JsonDeserialize(converter = LocalDateTimeToMillisecondsConverter.class)
    @JsonSerialize(converter = MillisecondsToLocalDateTimeConverter.class)
    private LocalDateTime dtCreate;

    @JsonProperty(namespace = "dt_update")
    @JsonDeserialize(converter = LocalDateTimeToMillisecondsConverter.class)
    @JsonSerialize(converter = MillisecondsToLocalDateTimeConverter.class)
    private LocalDateTime dtUpdate;

    private ReportStatus status;

    private ReportType type;

    private String description;

    private Map<String, Object> params;

    public ReportDto() {
    }

    public ReportDto(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, ReportStatus status, ReportType type, String description, Map<String, Object> params) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.status = status;
        this.type = type;
        this.description = description;
        this.params = params;
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

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
