package by.it_academy.audit_service.core.dto;

import by.it_academy.audit_service.core.enums.EssenceType;
import by.it_academy.task_manager_common.support.json.converters.LocalDateTimeToMillisecondsConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuditDto {

    private UUID uuid;

    @JsonProperty(namespace = "dt_create")
    @JsonSerialize(converter = LocalDateTimeToMillisecondsConverter.class)
    private LocalDateTime dtCreate;

    private UserDto user;

    private String text;

    private EssenceType type;

    private String id;

    public AuditDto() {
    }

    public AuditDto(UUID uuid, LocalDateTime dtCreate, UserDto user, String text, EssenceType type, String id) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.user = user;
        this.text = text;
        this.type = type;
        this.id = id;
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EssenceType getType() {
        return type;
    }

    public void setType(EssenceType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
