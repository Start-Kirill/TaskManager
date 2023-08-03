package by.it_academy.user_service.service.exceptions.common;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonErrorException;

import java.util.List;

public class NotVerifyUserException extends CommonErrorException {
    public NotVerifyUserException(List<ErrorResponse> errors) {
        super(errors);
    }

    public NotVerifyUserException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public NotVerifyUserException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public NotVerifyUserException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public NotVerifyUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
