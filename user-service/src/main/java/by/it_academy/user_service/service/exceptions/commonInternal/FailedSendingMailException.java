package by.it_academy.user_service.service.exceptions.commonInternal;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonInternalErrorException;

import java.util.List;

public class FailedSendingMailException extends CommonInternalErrorException {
    public FailedSendingMailException(List<ErrorResponse> errors) {
        super(errors);
    }

    public FailedSendingMailException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public FailedSendingMailException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public FailedSendingMailException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public FailedSendingMailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
