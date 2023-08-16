package by.it_academy.user_service.service.exceptions.common;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonErrorException;

import java.util.List;

public class NotActivatedUserException extends CommonErrorException {
    public NotActivatedUserException(List<ErrorResponse> errors) {
        super(errors);
    }

    public NotActivatedUserException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public NotActivatedUserException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public NotActivatedUserException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public NotActivatedUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
