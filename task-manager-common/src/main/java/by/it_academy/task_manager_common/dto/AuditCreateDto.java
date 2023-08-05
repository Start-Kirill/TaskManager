package by.it_academy.task_manager_common.dto;

import by.it_academy.task_manager_common.enums.EssenceType;

public class AuditCreateDto {

    private String userToken;

    private String text;

    private EssenceType type;

    private String id;

    public AuditCreateDto() {
    }

    public AuditCreateDto(String userToken, String text, EssenceType type, String id) {
        this.userToken = userToken;
        this.text = text;
        this.type = type;
        this.id = id;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
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
