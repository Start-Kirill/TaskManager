package by.it_academy.report_service.service.exceptions;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonInternalErrorException;

import java.util.List;

public class NotPossibleFormReportException extends CommonInternalErrorException {
    public NotPossibleFormReportException(List<ErrorResponse> errors) {
        super(errors);
    }

    public NotPossibleFormReportException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public NotPossibleFormReportException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public NotPossibleFormReportException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public NotPossibleFormReportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
