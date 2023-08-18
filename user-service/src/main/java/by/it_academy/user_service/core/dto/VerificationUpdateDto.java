package by.it_academy.user_service.core.dto;

import by.it_academy.user_service.core.enums.VerificationStatus;
import by.it_academy.user_service.dao.entity.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class VerificationUpdateDto {

    private User user;

    private String url;

    private String code;

    @Enumerated(EnumType.STRING)
    private VerificationStatus status;

    private Long attempt;


    public VerificationUpdateDto() {
    }

    public VerificationUpdateDto(User user, String url, String code, VerificationStatus status, Long attempt) {
        this.user = user;
        this.url = url;
        this.code = code;
        this.status = status;
        this.attempt = attempt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public VerificationStatus getStatus() {
        return status;
    }

    public void setStatus(VerificationStatus status) {
        this.status = status;
    }

    public Long getAttempt() {
        return attempt;
    }

    public void setAttempt(Long attempt) {
        this.attempt = attempt;
    }
}

