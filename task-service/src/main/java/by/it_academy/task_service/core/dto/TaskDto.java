package by.it_academy.task_service.core.dto;

import by.it_academy.task_manager_common.support.json.converters.LocalDateTimeToMillisecondsConverter;
import by.it_academy.task_manager_common.support.json.converters.MillisecondsToLocalDateTimeConverter;
import by.it_academy.task_service.core.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.UUID;

public class TaskDto {

    private UUID uuid;

    @JsonProperty("dt_create")
    @JsonSerialize(converter = LocalDateTimeToMillisecondsConverter.class)
    @JsonDeserialize(converter = MillisecondsToLocalDateTimeConverter.class)
    private LocalDateTime dtCreate;

    @JsonProperty("dt_update")
    @JsonSerialize(converter = LocalDateTimeToMillisecondsConverter.class)
    @JsonDeserialize(converter = MillisecondsToLocalDateTimeConverter.class)
    private LocalDateTime dtUpdate;

    private ProjectRef project;

    private String title;

    private String description;

    private TaskStatus status;

    private UserRef implementer;

    public TaskDto() {
    }

    public TaskDto(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, ProjectRef project, String title, String description, TaskStatus status, UserRef implementer) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.project = project;
        this.title = title;
        this.description = description;
        this.status = status;
        this.implementer = implementer;
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

    public ProjectRef getProject() {
        return project;
    }

    public void setProject(ProjectRef project) {
        this.project = project;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public UserRef getImplementer() {
        return implementer;
    }

    public void setImplementer(UserRef implementer) {
        this.implementer = implementer;
    }
}
