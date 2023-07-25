package by.it_academy.user_service.core.dto;

public class UserRegistrationDto {

    private String mail;

    private String fio;

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
