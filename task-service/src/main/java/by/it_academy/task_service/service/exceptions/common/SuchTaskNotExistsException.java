package by.it_academy.task_service.service.exceptions.common;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonErrorException;

import java.util.List;

public class SuchTaskNotExistsException extends CommonErrorException {
    public SuchTaskNotExistsException(List<ErrorResponse> errors) {
        super(errors);
    }

    public SuchTaskNotExistsException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public SuchTaskNotExistsException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public SuchTaskNotExistsException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public SuchTaskNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
