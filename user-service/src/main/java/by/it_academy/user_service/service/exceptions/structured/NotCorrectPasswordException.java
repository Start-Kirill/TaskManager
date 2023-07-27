package by.it_academy.user_service.service.exceptions.structured;

import by.it_academy.user_service.core.dto.exceptions.structured.StructuredErrorException;

import java.util.Map;

public class NotCorrectPasswordException extends StructuredErrorException {
    public NotCorrectPasswordException(Map<String, String> errors) {
        super(errors);
    }

    public NotCorrectPasswordException(String message, Map<String, String> errors) {
        super(message, errors);
    }

    public NotCorrectPasswordException(String message, Throwable cause, Map<String, String> errors) {
        super(message, cause, errors);
    }

    public NotCorrectPasswordException(Throwable cause, Map<String, String> errors) {
        super(cause, errors);
    }

    public NotCorrectPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
