package by.it_academy.report_service.service.exceptions;

import by.it_academy.task_manager_common.exceptions.StructuredErrorException;

import java.util.Map;

public class InvalidReportParamsException extends StructuredErrorException {
    public InvalidReportParamsException(Map<String, String> errors) {
        super(errors);
    }

    public InvalidReportParamsException(String message, Map<String, String> errors) {
        super(message, errors);
    }

    public InvalidReportParamsException(String message, Throwable cause, Map<String, String> errors) {
        super(message, cause, errors);
    }

    public InvalidReportParamsException(Throwable cause, Map<String, String> errors) {
        super(cause, errors);
    }

    public InvalidReportParamsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
