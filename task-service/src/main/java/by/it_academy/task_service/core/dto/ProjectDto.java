package by.it_academy.task_service.core.dto;

import by.it_academy.task_manager_common.support.json.converters.LocalDateTimeToMillisecondsConverter;
import by.it_academy.task_manager_common.support.json.converters.MillisecondsToLocalDateTimeConverter;
import by.it_academy.task_service.core.enums.ProjectStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ProjectDto {

    private UUID uuid;

    @JsonProperty("dt_create")
    @JsonSerialize(converter =  LocalDateTimeToMillisecondsConverter.class)
    @JsonDeserialize(converter = MillisecondsToLocalDateTimeConverter.class)
    private LocalDateTime dtCreate;

    @JsonProperty("dt_update")
    @JsonSerialize(converter =  LocalDateTimeToMillisecondsConverter.class)
    @JsonDeserialize(converter = MillisecondsToLocalDateTimeConverter.class)
    private LocalDateTime dtUpdate;

    private String name;

    private String description;

    private UserRef manager;

    private List<UserRef> staff;

    private ProjectStatus status;

    public ProjectDto() {
    }

    public ProjectDto(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, String name, String description, UserRef manager, List<UserRef> staff, ProjectStatus status) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.staff = staff;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserRef getManager() {
        return manager;
    }

    public void setManager(UserRef manager) {
        this.manager = manager;
    }

    public List<UserRef> getStaff() {
        return staff;
    }

    public void setStaff(List<UserRef> staff) {
        this.staff = staff;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }
}
