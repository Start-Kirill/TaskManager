package by.it_academy.user_service.service.exceptions.common;

import by.it_academy.user_service.core.dto.exceptions.common.CommonErrorException;
import by.it_academy.user_service.core.errors.ErrorResponse;

import java.util.List;

public class InternalServerErrorException extends CommonErrorException {

    public InternalServerErrorException(List<ErrorResponse> errors) {
        super(errors);
    }

    public InternalServerErrorException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public InternalServerErrorException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public InternalServerErrorException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public InternalServerErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
