package by.it_academy.taskManagerDto.dto;


import by.it_academy.taskManagerDto.enums.ErrorType;
import by.it_academy.taskManagerDto.support.StructuredErrorConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Map;

public class StructuredErrorResponse {

    private ErrorType logref;

    @JsonSerialize(converter = StructuredErrorConverter.class)
    private Map<String, String> errors;

    public StructuredErrorResponse() {
    }

    public StructuredErrorResponse(ErrorType logref, Map<String, String> errors) {
        this.logref = logref;
        this.errors = errors;
    }

    public ErrorType getLogref() {
        return logref;
    }

    public void setLogref(ErrorType logref) {
        this.logref = logref;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
