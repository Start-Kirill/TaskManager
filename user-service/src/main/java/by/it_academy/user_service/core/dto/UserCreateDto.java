package by.it_academy.user_service.core.dto;

import by.it_academy.task_manager_common.enums.UserRole;
import by.it_academy.task_manager_common.enums.UserStatus;
import by.it_academy.user_service.endpoints.web.support.json.UserRoleConverter;
import by.it_academy.user_service.endpoints.web.support.json.UserStatusConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class UserCreateDto {

    private String mail;

    private String fio;

    @JsonDeserialize(converter = UserRoleConverter.class)
    private UserRole role;


    @JsonDeserialize(converter = UserStatusConverter.class)
    private UserStatus status;

    private String password;


    public UserCreateDto() {
    }

    public UserCreateDto(String mail, String fio, UserRole role, UserStatus status, String password) {
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
