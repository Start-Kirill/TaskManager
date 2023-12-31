package by.it_academy.task_manager_common.exceptions;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;

import java.io.IOException;
import java.util.List;

public class CommonErrorException extends RuntimeException {

    private List<ErrorResponse> errors;

    public CommonErrorException(List<ErrorResponse> errors) {
        this.errors = errors;
    }

    public CommonErrorException(String message, List<ErrorResponse> errors) {
        super(message);
        this.errors = errors;
    }

    public CommonErrorException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause);
        this.errors = errors;
    }

    public CommonErrorException(Throwable cause, List<ErrorResponse> errors) {
        super(cause);
        this.errors = errors;
    }

    public CommonErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errors = errors;
    }

    public List<ErrorResponse> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorResponse> errors) {
        this.errors = errors;
    }
}
