package by.it_academy.user_service.core.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UserRegistrationDto {

    @NotNull(message = "Mail field is missing")
    @NotEmpty(message = "Mail must not to be empty")
    private String mail;


    @NotNull(message = "Fio field is missing")
    @NotEmpty(message = "Fio must not to be empty")
    private String fio;

    @NotNull(message = "Password field is missing")
    @NotEmpty(message = "Password must not to be empty")
    private String password;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String mail, String fio, String password) {
        this.mail = mail;
        this.fio = fio;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
