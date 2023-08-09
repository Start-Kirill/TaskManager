package by.it_academy.task_manager_common.exceptions.common;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonErrorException;

import java.util.List;

public class NotValidTokenSignatureException extends CommonErrorException {
    public NotValidTokenSignatureException(List<ErrorResponse> errors) {
        super(errors);
    }

    public NotValidTokenSignatureException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public NotValidTokenSignatureException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public NotValidTokenSignatureException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public NotValidTokenSignatureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
