package by.it_academy.report_service.service.exceptions;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonErrorException;

import java.util.List;

public class ReportNotDoneException extends CommonErrorException {
    public ReportNotDoneException(List<ErrorResponse> errors) {
        super(errors);
    }

    public ReportNotDoneException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public ReportNotDoneException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public ReportNotDoneException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public ReportNotDoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
