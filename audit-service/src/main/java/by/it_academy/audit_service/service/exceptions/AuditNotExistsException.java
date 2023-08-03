package by.it_academy.audit_service.service.exceptions;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.exceptions.CommonErrorException;

import java.util.List;

public class AuditNotExistsException extends CommonErrorException {
    public AuditNotExistsException(List<ErrorResponse> errors) {
        super(errors);
    }

    public AuditNotExistsException(String message, List<ErrorResponse> errors) {
        super(message, errors);
    }

    public AuditNotExistsException(String message, Throwable cause, List<ErrorResponse> errors) {
        super(message, cause, errors);
    }

    public AuditNotExistsException(Throwable cause, List<ErrorResponse> errors) {
        super(cause, errors);
    }

    public AuditNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ErrorResponse> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
