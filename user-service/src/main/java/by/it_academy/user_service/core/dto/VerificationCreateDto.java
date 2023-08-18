package by.it_academy.user_service.core.dto;

import by.it_academy.user_service.dao.entity.User;

public class VerificationCreateDto {

    private User user;


    public VerificationCreateDto() {
    }

    public VerificationCreateDto(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
