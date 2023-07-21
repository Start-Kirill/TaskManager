package by.it_academy.user_service.core.dto.exceptions;

public class NotCorrectUserRoleException extends RuntimeException {

    public NotCorrectUserRoleException() {
    }

    public NotCorrectUserRoleException(String message) {
        super(message);
    }

    public NotCorrectUserRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotCorrectUserRoleException(Throwable cause) {
        super(cause);
    }

    public NotCorrectUserRoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
