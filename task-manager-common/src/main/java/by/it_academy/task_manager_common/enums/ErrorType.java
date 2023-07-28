package by.it_academy.task_manager_common.enums;

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
