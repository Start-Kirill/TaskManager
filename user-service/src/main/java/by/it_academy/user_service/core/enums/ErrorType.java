package by.it_academy.user_service.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorType {
    ERROR("error"), STRUCTURED_ERROR("structured_error");

    private String name;

    ErrorType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
