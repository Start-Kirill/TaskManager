package by.it_academy.task_service.service.exceptions.security;

import by.it_academy.task_manager_common.exceptions.AccessForbiddenException;

public class UserAccessDeniedException extends AccessForbiddenException {

    public UserAccessDeniedException() {
    }

    public UserAccessDeniedException(String message) {
        super(message);
    }

    public UserAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAccessDeniedException(Throwable cause) {
        super(cause);
    }

    public UserAccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
