package by.it_academy.user_service.service.exceptions.commonInternal;

import by.it_academy.user_service.core.errors.ErrorResponse;
import by.it_academy.user_service.core.exceptions.CommonInternalErrorException;

import java.util.List;

public class UnknownConstraintException extends CommonInternalErrorException {

    public UnknownConstraintException(List<ErrorResponse> errors) {
        super(errors);
    }

    public UnknownConstraintException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public UnknownConstraintException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public UnknownConstraintException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public UnknownConstraintException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
