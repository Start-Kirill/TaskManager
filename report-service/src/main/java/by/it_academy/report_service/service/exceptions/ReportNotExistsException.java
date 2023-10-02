package by.it_academy.report_service.service.exceptions;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonErrorException;

import java.util.List;

public class ReportNotExistsException extends CommonErrorException {
    public ReportNotExistsException(List<ErrorResponse> errors) {
        super(errors);
    }

    public ReportNotExistsException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public ReportNotExistsException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public ReportNotExistsException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public ReportNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
