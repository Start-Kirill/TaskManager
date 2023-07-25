package by.it_academy.user_service.endpoints.web;

import by.it_academy.user_service.core.dto.exceptions.NotCorrectUserRoleException;
import by.it_academy.user_service.core.dto.exceptions.NotCorrectUserStatusException;
import by.it_academy.user_service.core.enums.ErrorType;
import by.it_academy.user_service.core.errors.ErrorResponse;
import by.it_academy.user_service.core.errors.SpecificError;
import by.it_academy.user_service.core.errors.StructuredErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final String ROLE_FIELD_NAME = "role";

    private static final String STATUS_FIELD_NAME = "role";

    private static final String UUID_FIELD_NAME = "uuid";

    private static final String DT_UPDATE_FIELD = "dt_update";

    private static final String PAGE_PARAM_NAME = "page";

    private static final String SIZE_PARAM_NAME = "size";


    @ExceptionHandler(NotCorrectUserRoleException.class)
    public ResponseEntity<?> handlerNotCorrectUserRoleException(NotCorrectUserRoleException ex) {

        StructuredErrorResponse structuredErrorResponse = makeStructuredError(ex.getMessage(), ROLE_FIELD_NAME);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(structuredErrorResponse);
    }

    @ExceptionHandler(NotCorrectUserStatusException.class)
    public ResponseEntity<?> handlerNotCorrectUserStatusException(NotCorrectUserStatusException ex) {

        StructuredErrorResponse structuredErrorResponse = makeStructuredError(ex.getMessage(), STATUS_FIELD_NAME);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(structuredErrorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        if (ex.getPropertyName().equals(UUID_FIELD_NAME)) {
            StructuredErrorResponse structuredErrorResponse = makeStructuredError("Invalid UUID. Change the request and repeat", UUID_FIELD_NAME);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(structuredErrorResponse);
        } else if (ex.getPropertyName().equals(DT_UPDATE_FIELD)) {
            StructuredErrorResponse structuredErrorResponse = makeStructuredError("Invalid update date(version). Change the request and repeat", DT_UPDATE_FIELD);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(structuredErrorResponse);
        } else if (ex.getPropertyName().equals(PAGE_PARAM_NAME)) {
            StructuredErrorResponse structuredErrorResponse = makeStructuredError("Invalid page value. Change the request and repeat", PAGE_PARAM_NAME);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(structuredErrorResponse);
        } else if (ex.getPropertyName().equals(SIZE_PARAM_NAME)) {
            StructuredErrorResponse structuredErrorResponse = makeStructuredError("Invalid size value. Change the request and repeat", SIZE_PARAM_NAME);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(structuredErrorResponse);
        } else {
            List<ErrorResponse> errorResponses = new ArrayList<>();
            errorResponses.add(new ErrorResponse(ErrorType.ERROR, "The request contains invalid data. Change the request and send it again"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponses);
        }
    }


    private StructuredErrorResponse makeStructuredError(String message, String field) {
        List<SpecificError> errors = new ArrayList<>();
        errors.add(new SpecificError(message, field));

        StructuredErrorResponse structuredErrorResponse = new StructuredErrorResponse();
        structuredErrorResponse.setErrors(errors);
        structuredErrorResponse.setLogref(ErrorType.STRUCTURED_ERROR);

        return structuredErrorResponse;
    }

}
