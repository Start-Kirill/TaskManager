package by.it_academy.task_manager_common.dto;

import by.it_academy.task_manager_common.enums.EssenceType;

public class AuditCreateDto {

    private UserDto user;

    private String text;

    private EssenceType type;

    private String id;

    public AuditCreateDto() {
    }

    public AuditCreateDto(UserDto user, String text, EssenceType type, String id) {
        this.user = user;
        this.text = text;
        this.type = type;
        this.id = id;
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
