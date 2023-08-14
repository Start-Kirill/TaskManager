package by.it_academy.task_manager_common.exceptions.commonInternal;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonInternalErrorException;

import java.util.List;

public class GeneratedDataNotCorrectException extends CommonInternalErrorException {

    public GeneratedDataNotCorrectException(List<ErrorResponse> errors) {
        super(errors);
    }

    public GeneratedDataNotCorrectException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public GeneratedDataNotCorrectException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public GeneratedDataNotCorrectException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public GeneratedDataNotCorrectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
