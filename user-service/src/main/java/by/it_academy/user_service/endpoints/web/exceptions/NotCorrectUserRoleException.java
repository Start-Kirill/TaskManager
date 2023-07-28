package by.it_academy.user_service.endpoints.web.exceptions;

import by.it_academy.user_service.core.exceptions.StructuredErrorException;

import java.util.Map;

public class NotCorrectUserRoleException extends StructuredErrorException {

    public NotCorrectUserRoleException(Map<String, String> errors) {
        super(errors);
    }

    public NotCorrectUserRoleException(String message, Map<String, String> errors) {
        super(message, errors);
    }

    public NotCorrectUserRoleException(String message, Throwable cause, Map<String, String> errors) {
        super(message, cause, errors);
    }

    public NotCorrectUserRoleException(Throwable cause, Map<String, String> errors) {
        super(cause, errors);
    }

    public NotCorrectUserRoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
