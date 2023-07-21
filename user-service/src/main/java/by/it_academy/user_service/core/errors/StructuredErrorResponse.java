package by.it_academy.user_service.core.errors;

import by.it_academy.user_service.core.enums.ErrorType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class StructuredErrorResponse {


    private ErrorType logref;

    private List<SpecificError> errors;

    public StructuredErrorResponse() {
    }

    public StructuredErrorResponse(ErrorType logref, List<SpecificError> errors) {
        this.logref = logref;
        this.errors = errors;
    }

    public ErrorType getLogref() {
        return logref;
    }

    public void setLogref(ErrorType logref) {
        this.logref = logref;
    }

    public List<SpecificError> getErrors() {
        return errors;
    }

    public void setErrors(List<SpecificError> errors) {
        this.errors = errors;
    }
}
