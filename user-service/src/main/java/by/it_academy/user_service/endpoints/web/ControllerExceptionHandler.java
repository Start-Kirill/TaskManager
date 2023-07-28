package by.it_academy.user_service.endpoints.web;

import by.it_academy.user_service.core.enums.ErrorType;
import by.it_academy.user_service.core.errors.ErrorResponse;
import by.it_academy.user_service.core.errors.StructuredErrorResponse;
import by.it_academy.user_service.core.exceptions.CommonErrorException;
import by.it_academy.user_service.core.exceptions.CommonInternalErrorException;
import by.it_academy.user_service.core.exceptions.StructuredErrorException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

@RestControllerAdvice
public class ControllerExceptionHandler {


    private static final String UUID_FIELD_NAME = "uuid";

    private static final String DT_UPDATE_FIELD = "dt_update";

    private static final String PAGE_PARAM_NAME = "page";

    private static final String SIZE_PARAM_NAME = "size";

    private static final String FIO_FIELD_NAME = "fio";

    private static final String MAIL_FIELD_NAME = "mail";

    private static final String PASSWORD_FIELD_NAME = "password";

    private static final String ROLE_FIELD_NAME = "role";

    private static final String STATUS_FIELD_NAME = "status";

    private static final String FIO_DTO_FIELD_NAME = "fio";

    private static final String MAIL_DTO_FIELD_NAME = "mail";

    private static final String PASSWORD_DTO_FIELD_NAME = "password";

    private static final String ROLE_DTO_FIELD_NAME = "role";

    private static final String STATUS_DTO_FIELD_NAME = "status";


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        List<ErrorResponse> error = new ArrayList<>();
        if (UUID_FIELD_NAME.equals(ex.getPropertyName())) {
            error.add(new ErrorResponse(ErrorType.ERROR, "Invalid UUID. Change the request and repeat"));
        } else if (DT_UPDATE_FIELD.equals(ex.getPropertyName())) {
            error.add(new ErrorResponse(ErrorType.ERROR, "Invalid update date(version). Change the request and repeat"));
        } else if (PAGE_PARAM_NAME.equals(ex.getPropertyName())) {
            error.add(new ErrorResponse(ErrorType.ERROR, "Invalid page value. Change the request and repeat"));
        } else if (SIZE_PARAM_NAME.equals(ex.getPropertyName())) {
            error.add(new ErrorResponse(ErrorType.ERROR, "Invalid size value. Change the request and repeat"));
        } else {
            error.add(new ErrorResponse(ErrorType.ERROR, "The request contains invalid data. Change the request and send it again"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        String field = fieldError.getField();
        String message = fieldError.getDefaultMessage();
        Map<String, String> errors = new HashMap<>();
        switch (field) {
            case FIO_DTO_FIELD_NAME -> errors.put(FIO_FIELD_NAME, message);
            case MAIL_DTO_FIELD_NAME -> errors.put(MAIL_FIELD_NAME, message);
            case ROLE_DTO_FIELD_NAME -> errors.put(ROLE_FIELD_NAME, message);
            case STATUS_DTO_FIELD_NAME -> errors.put(STATUS_FIELD_NAME, message);
            case PASSWORD_DTO_FIELD_NAME -> errors.put(PASSWORD_FIELD_NAME, message);
        }

        if (errors.isEmpty()) {
            List<ErrorResponse> errorResponses = new ArrayList<>();
            errorResponses.add(new ErrorResponse(ErrorType.ERROR, "The request contains invalid data. Change the request and send it again"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponses);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StructuredErrorResponse(ErrorType.STRUCTURED_ERROR, errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handlerConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> c : constraintViolations) {
            Path propertyPath = c.getPropertyPath();
            Iterator<Path.Node> iterator = propertyPath.iterator();
            String messageTemplate = c.getMessageTemplate();
            while (iterator.hasNext()) {
                Path.Node next = iterator.next();
                if (ElementKind.PARAMETER.equals(next.getKind())) {
                    errors.put(next.getName(), messageTemplate);
                }
            }
        }
        if (errors.isEmpty()) {
            List<ErrorResponse> errorResponses = new ArrayList<>();
            errorResponses.add(new ErrorResponse(ErrorType.ERROR, "The request contains invalid data. Change the request and send it again"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponses);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StructuredErrorResponse(ErrorType.STRUCTURED_ERROR, errors));
    }


    @ExceptionHandler(StructuredErrorException.class)
    public ResponseEntity<?> handlerStructuredErrorException(StructuredErrorException ex) {
        Map<String, String> errors = ex.getErrors();
        StructuredErrorResponse structuredErrorResponse = new StructuredErrorResponse(ErrorType.STRUCTURED_ERROR, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(structuredErrorResponse);
    }

    @ExceptionHandler(CommonErrorException.class)
    public ResponseEntity<?> handlerCommonErrorException(CommonErrorException ex) {
        List<ErrorResponse> errors = ex.getErrors();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(CommonInternalErrorException.class)
    public ResponseEntity<?> handlerCommonInternalErrorException(CommonInternalErrorException ex) {
        List<ErrorResponse> errors = ex.getErrors();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handlerHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        List<ErrorResponse> errors = new ArrayList<>();
        errors.add(new ErrorResponse(ErrorType.ERROR, "Passed data is incorrect or not enough"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handlerNullPointerException(NullPointerException ex) {
        List<ErrorResponse> errors = new ArrayList<>();
        errors.add(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator"));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }


}
