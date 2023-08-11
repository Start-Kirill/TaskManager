package by.it_academy.task_service.service.exceptions.common;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonErrorException;

import java.util.List;

public class SuchProjectNotExistsException extends CommonErrorException {
    public SuchProjectNotExistsException(List<ErrorResponse> errors) {
        super(errors);
    }

    public SuchProjectNotExistsException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public SuchProjectNotExistsException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public SuchProjectNotExistsException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public SuchProjectNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
