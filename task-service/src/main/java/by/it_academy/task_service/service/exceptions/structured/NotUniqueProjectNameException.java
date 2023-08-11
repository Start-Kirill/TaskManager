package by.it_academy.task_service.service.exceptions.structured;

import by.it_academy.task_manager_common.exceptions.StructuredErrorException;

import java.util.Map;

public class NotUniqueProjectNameException extends StructuredErrorException {
    public NotUniqueProjectNameException(Map<String, String> errors) {
        super(errors);
    }

    public NotUniqueProjectNameException(String message, Map<String, String> errors) {
        super(message, errors);
    }

    public NotUniqueProjectNameException(String message, Throwable cause, Map<String, String> errors) {
        super(message, cause, errors);
    }

    public NotUniqueProjectNameException(Throwable cause, Map<String, String> errors) {
        super(cause, errors);
    }

    public NotUniqueProjectNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
