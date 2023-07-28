package by.it_academy.user_service.service.exceptions.structured;

import by.it_academy.user_service.core.exceptions.StructuredErrorException;

import java.util.Map;

public class MailNotExistsException extends StructuredErrorException {
    public MailNotExistsException(Map<String, String> errors) {
        super(errors);
    }

    public MailNotExistsException(String message, Map<String, String> errors) {
        super(message, errors);
    }

    public MailNotExistsException(String message, Throwable cause, Map<String, String> errors) {
        super(message, cause, errors);
    }

    public MailNotExistsException(Throwable cause, Map<String, String> errors) {
        super(cause, errors);
    }

    public MailNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors) {
        super(message, cause, enableSuppression, writableStackTrace, errors);
    }
}
