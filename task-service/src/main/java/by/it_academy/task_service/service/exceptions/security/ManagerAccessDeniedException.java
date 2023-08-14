package by.it_academy.task_service.service.exceptions.security;

import by.it_academy.task_manager_common.exceptions.AccessForbiddenException;

public class ManagerAccessDeniedException extends AccessForbiddenException {

    public ManagerAccessDeniedException() {
    }

    public ManagerAccessDeniedException(String message) {
        super(message);
    }

    public ManagerAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManagerAccessDeniedException(Throwable cause) {
        super(cause);
    }

    public ManagerAccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
