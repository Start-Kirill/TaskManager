package by.it_academy.user_service.core.dto;

import by.it_academy.user_service.endpoints.web.support.json.LocalDateTimeToMillisecondsConverter;
import by.it_academy.user_service.core.enums.UserRole;
import by.it_academy.user_service.core.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserDto {

    private UUID uuid;

    private String mail;

    private String fio;

    private UserRole role;

    private UserStatus status;


    @JsonProperty("dt_create")
    @JsonSerialize(converter = LocalDateTimeToMillisecondsConverter.class)
    private LocalDateTime dateTimeCreate;

    @JsonProperty("dt_update")
    @JsonSerialize(converter = LocalDateTimeToMillisecondsConverter.class)
    private LocalDateTime dateTimeUpdate;

    public UserDto() {
    }

    public UserDto(UUID uuid, String mail, String fio, UserRole role, UserStatus status, LocalDateTime dateTimeCreate, LocalDateTime dateTimeUpdate) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.dateTimeCreate = dateTimeCreate;
        this.dateTimeUpdate = dateTimeUpdate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }


    public LocalDateTime getDateTimeCreate() {
        return dateTimeCreate;
    }

    public void setDateTimeCreate(LocalDateTime dateTimeCreate) {
        this.dateTimeCreate = dateTimeCreate;
    }

    public LocalDateTime getDateTimeUpdate() {
        return dateTimeUpdate;
    }

    public void setDateTimeUpdate(LocalDateTime dateTimeUpdate) {
        this.dateTimeUpdate = dateTimeUpdate;
    }
}
