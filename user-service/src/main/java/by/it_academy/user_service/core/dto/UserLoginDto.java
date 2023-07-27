package by.it_academy.user_service.core.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UserLoginDto {

    @NotNull(message = "Mail field is missing")
    @NotEmpty(message = "Mail must not to be empty")
    private String mail;

    @NotNull(message = "Password field is missing")
    @NotEmpty(message = "Password must not to be empty")
    private String password;

    public UserLoginDto() {
    }

    public UserLoginDto(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
