package by.it_academy.user_service.endpoints.web;

import by.it_academy.user_service.core.dto.exceptions.NotCorrectUserRoleException;
import by.it_academy.user_service.core.dto.exceptions.NotCorrectUserStatusException;
import by.it_academy.user_service.core.enums.ErrorType;
import by.it_academy.user_service.core.errors.SpecificError;
import by.it_academy.user_service.core.errors.StructuredErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final String ROLE_FIELD_NAME = "role";

    private static final String STATUS_FIELD_NAME = "role";

    @ExceptionHandler(NotCorrectUserRoleException.class)
    public ResponseEntity<?> handlerNotCorrectUserRoleException(NotCorrectUserRoleException ex) {
        List<SpecificError> errors = new ArrayList<>();
        errors.add(new SpecificError(ex.getMessage(), ROLE_FIELD_NAME));

        StructuredErrorResponse structuredErrorResponse = new StructuredErrorResponse();
        structuredErrorResponse.setErrors(errors);
        structuredErrorResponse.setLogref(ErrorType.STRUCTURED_ERROR);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(structuredErrorResponse);
    }

    @ExceptionHandler(NotCorrectUserStatusException.class)
    public ResponseEntity<?> handlerNotCorrectUserStatusException(NotCorrectUserStatusException ex) {
        List<SpecificError> errors = new ArrayList<>();
        errors.add(new SpecificError(ex.getMessage(), STATUS_FIELD_NAME));

        StructuredErrorResponse structuredErrorResponse = new StructuredErrorResponse();
        structuredErrorResponse.setErrors(errors);
        structuredErrorResponse.setLogref(ErrorType.STRUCTURED_ERROR);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(structuredErrorResponse);
    }
}
