package by.it_academy.user_service.service.exceptions.structured;

import by.it_academy.user_service.core.dto.exceptions.structured.StructuredErrorException;

import java.util.Map;

public class NotValidUserBodyException extends StructuredErrorException {
    public NotValidUserBodyException(Map<String, String> errors) {
        super(errors);
    }

    public NotValidUserBodyException(String message, Map<String, String> errors) {
        super(message, errors);
    }

    public NotValidUserBodyException(String message, Throwable cause, Map<String, String> errors) {
        super(message, cause, errors);
    }

    public NotValidUserBodyException(Throwable cause, Map<String, String> errors) {
        super(cause, errors);
    }

    public NotValidUserBodyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
