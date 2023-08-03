package by.it_academy.user_service.endpoints.web.exceptions;

import by.it_academy.task_manager_common.exceptions.StructuredErrorException;

import java.util.Map;

public class NotCorrectUserStatusException extends StructuredErrorException {

    public NotCorrectUserStatusException(Map<String, String> errors) {
        super(errors);
    }

    public NotCorrectUserStatusException(String message, Map<String, String> errors) {
        super(message, errors);
    }

    public NotCorrectUserStatusException(String message, Throwable cause, Map<String, String> errors) {
        super(message, cause, errors);
    }

    public NotCorrectUserStatusException(Throwable cause, Map<String, String> errors) {
        super(cause, errors);
    }

    public NotCorrectUserStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
