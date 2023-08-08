package by.it_academy.task_manager_common.exceptions.common;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonErrorException;

import java.util.List;

public class ExpiredTokenException extends CommonErrorException {
    public ExpiredTokenException(List<ErrorResponse> errors) {
        super(errors);
    }

    public ExpiredTokenException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public ExpiredTokenException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public ExpiredTokenException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public ExpiredTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
