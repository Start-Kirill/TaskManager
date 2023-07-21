package by.it_academy.user_service.core.dto.exceptions;

public class NotCorrectUserStatusException extends RuntimeException {
    public NotCorrectUserStatusException() {
    }

    public NotCorrectUserStatusException(String message) {
        super(message);
    }

    public NotCorrectUserStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotCorrectUserStatusException(Throwable cause) {
        super(cause);
    }

    public NotCorrectUserStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
