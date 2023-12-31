package by.it_academy.user_service.service.exceptions.common;

import by.it_academy.task_manager_common.exceptions.CommonErrorException;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;

import java.util.List;

public class UserNotExistsException extends CommonErrorException {
    public UserNotExistsException(List<ErrorResponse> errors) {
        super(errors);
    }

    public UserNotExistsException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public UserNotExistsException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public UserNotExistsException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public UserNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
