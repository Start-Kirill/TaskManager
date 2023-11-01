package by.it_academy.report_service.service.exceptions;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonInternalErrorException;

import java.util.List;

public class NotPossibleReadDataException extends CommonInternalErrorException {
    public NotPossibleReadDataException(List<ErrorResponse> errors) {
        super(errors);
    }

    public NotPossibleReadDataException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public NotPossibleReadDataException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public NotPossibleReadDataException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public NotPossibleReadDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
