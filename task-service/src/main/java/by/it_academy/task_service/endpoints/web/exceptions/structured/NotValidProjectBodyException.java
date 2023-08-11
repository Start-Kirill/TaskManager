package by.it_academy.task_service.endpoints.web.exceptions.structured;

import by.it_academy.task_manager_common.exceptions.StructuredErrorException;

import java.util.Map;

public class NotValidProjectBodyException extends StructuredErrorException {
    public NotValidProjectBodyException(Map<String, String> errors) {
        super(errors);
    }

    public NotValidProjectBodyException(String message, Map<String, String> errors) {
        super(message, errors);
    }

    public NotValidProjectBodyException(String message, Throwable cause, Map<String, String> errors) {
        super(message, cause, errors);
    }

    public NotValidProjectBodyException(Throwable cause, Map<String, String> errors) {
        super(cause, errors);
    }

    public NotValidProjectBodyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
