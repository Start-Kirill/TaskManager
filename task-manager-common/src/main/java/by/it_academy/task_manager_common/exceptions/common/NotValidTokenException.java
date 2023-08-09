package by.it_academy.task_manager_common.exceptions.common;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonErrorException;

import java.util.List;

public class NotValidTokenException extends CommonErrorException {
    public NotValidTokenException(List<ErrorResponse> errors) {
        super(errors);
    }

    public NotValidTokenException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public NotValidTokenException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public NotValidTokenException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public NotValidTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
