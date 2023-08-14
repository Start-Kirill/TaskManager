package by.it_academy.task_service.service.exceptions.structured;

import by.it_academy.task_manager_common.exceptions.StructuredErrorException;

import java.util.Map;

public class StaffNotExistsException extends StructuredErrorException {
    public StaffNotExistsException(Map<String, String> errors) {
        super(errors);
    }

    public StaffNotExistsException(String message, Map<String, String> errors) {
        super(message, errors);
    }

    public StaffNotExistsException(String message, Throwable cause, Map<String, String> errors) {
        super(message, cause, errors);
    }

    public StaffNotExistsException(Throwable cause, Map<String, String> errors) {
        super(cause, errors);
    }

    public StaffNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
