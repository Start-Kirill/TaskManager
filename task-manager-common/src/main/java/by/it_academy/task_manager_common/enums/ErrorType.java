package by.it_academy.task_manager_common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorType {

    ERROR, STRUCTURED_ERROR;

    @JsonValue
    public String toLowerCase() {
        return this.name().toLowerCase();
    }

}
