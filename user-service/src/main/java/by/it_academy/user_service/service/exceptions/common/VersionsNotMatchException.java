package by.it_academy.user_service.service.exceptions.common;

import by.it_academy.task_manager_common.exceptions.CommonErrorException;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;

import java.util.List;

public class VersionsNotMatchException extends CommonErrorException {
    public VersionsNotMatchException(List<ErrorResponse> errors) {
        super(errors);
    }

    public VersionsNotMatchException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public VersionsNotMatchException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public VersionsNotMatchException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public VersionsNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
