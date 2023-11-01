package by.it_academy.report_service.service.exceptions;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonInternalErrorException;

import java.util.List;

public class NotPossiblePutDataException extends CommonInternalErrorException {

    public NotPossiblePutDataException(List<ErrorResponse> errors) {
        super(errors);
    }

    public NotPossiblePutDataException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public NotPossiblePutDataException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public NotPossiblePutDataException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public NotPossiblePutDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
