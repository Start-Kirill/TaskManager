package by.it_academy.task_manager_common.exceptions.structured;

import by.it_academy.task_manager_common.exceptions.StructuredErrorException;

import java.util.Map;

public class NotCorrectPageDataException extends StructuredErrorException {
    public NotCorrectPageDataException(Map<String, String> errors) {
        super(errors);
    }

    public NotCorrectPageDataException(String message, Map<String, String> errors) {
        super(message, errors);
    }

    public NotCorrectPageDataException(String message, Throwable cause, Map<String, String> errors) {
        super(message, cause, errors);
    }

    public NotCorrectPageDataException(Throwable cause, Map<String, String> errors) {
        super(cause, errors);
    }

    public NotCorrectPageDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
