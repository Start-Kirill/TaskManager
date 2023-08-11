package by.it_academy.task_manager_common.exceptions.commonInternal;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonInternalErrorException;

import java.util.List;

public class FeignErrorException extends CommonInternalErrorException {
    public FeignErrorException(List<ErrorResponse> errors) {
        super(errors);
    }

    public FeignErrorException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public FeignErrorException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public FeignErrorException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public FeignErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
