package by.it_academy.user_service.service.exceptions.common;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonErrorException;

import java.util.List;

public class CodeForUserExistsException extends CommonErrorException {
    public CodeForUserExistsException(List<ErrorResponse> errors) {
        super(errors);
    }

    public CodeForUserExistsException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public CodeForUserExistsException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public CodeForUserExistsException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public CodeForUserExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
