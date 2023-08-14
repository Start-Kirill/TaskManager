package by.it_academy.task_manager_common.exceptions.commonInternal;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonInternalErrorException;

import java.util.List;

public class InternalServerErrorException extends CommonInternalErrorException {

    public InternalServerErrorException(List<ErrorResponse> errors) {
        super(errors);
    }

    public InternalServerErrorException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public InternalServerErrorException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public InternalServerErrorException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public InternalServerErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
