package by.it_academy.task_service.service.exceptions.structured;

import by.it_academy.task_manager_common.exceptions.StructuredErrorException;

import java.util.Map;

public class NotValidTaskBodyException extends StructuredErrorException {

    public NotValidTaskBodyException(Map<String, String> errors) {
        super(errors);
    }

    public NotValidTaskBodyException(String message, Map<String, String> errors) {
        super(message, errors);
    }

    public NotValidTaskBodyException(String message, Throwable cause, Map<String, String> errors) {
        super(message, cause, errors);
    }

    public NotValidTaskBodyException(Throwable cause, Map<String, String> errors) {
        super(cause, errors);
    }

    public NotValidTaskBodyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
