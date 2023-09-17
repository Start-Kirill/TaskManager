package by.it_academy.report_service.dao.exceptions;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.commonInternal.InternalServerErrorException;

import java.util.List;

public class SuchFileNotExists extends InternalServerErrorException {
    public SuchFileNotExists(List<ErrorResponse> errors) {
        super(errors);
    }

    public SuchFileNotExists(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public SuchFileNotExists(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public SuchFileNotExists(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public SuchFileNotExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
