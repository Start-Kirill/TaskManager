package by.it_academy.user_service.core.dto;

import by.it_academy.user_service.dao.entity.User;

public class VerificationCodeCreateDto {

    private String code;

    private User user;

    public VerificationCodeCreateDto() {
    }

    public VerificationCodeCreateDto(String code, User user) {
        this.code = code;
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
