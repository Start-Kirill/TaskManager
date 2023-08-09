package by.it_academy.task_service.endpoints.web.exceptions;

import by.it_academy.task_manager_common.exceptions.StructuredErrorException;

import java.util.Map;

public class NotValidTaskStatusException extends StructuredErrorException {
    public NotValidTaskStatusException(Map<String, String> errors) {
        super(errors);
    }

    public NotValidTaskStatusException(String message, Map<String, String> errors) {
        super(message, errors);
    }

    public NotValidTaskStatusException(String message, Throwable cause, Map<String, String> errors) {
        super(message, cause, errors);
    }

    public NotValidTaskStatusException(Throwable cause, Map<String, String> errors) {
        super(cause, errors);
    }

    public NotValidTaskStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
