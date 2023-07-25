package by.it_academy.user_service.service.exceptions.common;

import by.it_academy.user_service.core.dto.exceptions.common.CommonErrorException;
import by.it_academy.user_service.core.errors.ErrorResponse;

import java.util.List;

public class GeneratedDataNotCorrectException extends CommonErrorException {

    public GeneratedDataNotCorrectException(List<ErrorResponse> errors) {
        super(errors);
    }

    public GeneratedDataNotCorrectException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public GeneratedDataNotCorrectException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public GeneratedDataNotCorrectException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public GeneratedDataNotCorrectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
