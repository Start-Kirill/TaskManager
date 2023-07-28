package by.it_academy.user_service.endpoints.web.exceptions;

import by.it_academy.user_service.core.exceptions.CommonErrorException;
import by.it_academy.user_service.core.errors.ErrorResponse;

import java.util.List;

public class NotCorrectValueException extends CommonErrorException {
    public NotCorrectValueException(List<ErrorResponse> errors) {
        super(errors);
    }

    public NotCorrectValueException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public NotCorrectValueException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public NotCorrectValueException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public NotCorrectValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
