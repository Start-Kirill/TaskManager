package by.it_academy.user_service.core.errors;

import by.it_academy.user_service.core.enums.ErrorType;

public class ErrorResponse {

    private ErrorType logref;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorType logref, String message) {
        this.logref = logref;
        this.message = message;
    }

    public ErrorType getLogref() {
        return logref;
    }

    public void setLogref(ErrorType logref) {
        this.logref = logref;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
