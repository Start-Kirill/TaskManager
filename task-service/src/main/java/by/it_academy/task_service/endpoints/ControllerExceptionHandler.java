package by.it_academy.task_service.endpoints;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.dto.errors.StructuredErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.exceptions.AccessForbiddenException;
import by.it_academy.task_manager_common.exceptions.CommonErrorException;
import by.it_academy.task_manager_common.exceptions.CommonInternalErrorException;
import by.it_academy.task_manager_common.exceptions.StructuredErrorException;
import by.it_academy.task_manager_common.exceptions.common.ExpiredTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final String UUID_FIELD_NAME = "uuid";

    private static final String PAGE_PARAM_NAME = "page";

    private static final String SIZE_PARAM_NAME = "size";

    private static final String TASK_STATUS_PARAM_NAME = "status";

    private static final String DT_UPDATE_FIELD = "dt_update";

    private static final String INCORRECT_DATA_OR_NOT_ENOUGH_MESSAGE = "Passed data is incorrect or not enough";

    private static final String INVALID_UUID_MESSAGE = "Invalid UUID. Change the request and repeat";

    private static final String INVALID_PAGE_MESSAGE = "Invalid page value. Change the request and repeat";

    private static final String INVALID_SIZE_MESSAGE = "Invalid size value. Change the request and repeat";

    private static final String INVALID_STATUS_MESSAGE = "Invalid status value. Change the request and repeat";

    private static final String SERVER_INTERNAL_ERROR_MESSAGE = "The server was unable to process the request correctly. Please contact administrator";

    private static final String REQUEST_INVALID_DATA_MESSAGE = "The request contains invalid data. Change the request and send it again";

    private static final String INVALID_UPDATE_DATE_MESSAGE = "Invalid update date(version). Change the request and repeat";


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

    @ExceptionHandler(AccessForbiddenException.class)
    public ResponseEntity<?> handlerCommonInternalErrorException(AccessForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handlerHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        List<ErrorResponse> errors = new ArrayList<>();
        errors.add(new ErrorResponse(ErrorType.ERROR, INCORRECT_DATA_OR_NOT_ENOUGH_MESSAGE));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        List<ErrorResponse> error = new ArrayList<>();
        Map<String, String> mapErrors = new HashMap<>();
        if (UUID_FIELD_NAME.equals(ex.getPropertyName())) {
            error.add(new ErrorResponse(ErrorType.ERROR, INVALID_UUID_MESSAGE));
        } else if (PAGE_PARAM_NAME.equals(ex.getPropertyName())) {
            mapErrors.put(PAGE_PARAM_NAME, INVALID_PAGE_MESSAGE);
        } else if (SIZE_PARAM_NAME.equals(ex.getPropertyName())) {
            mapErrors.put(SIZE_PARAM_NAME, INVALID_SIZE_MESSAGE);
        } else if (TASK_STATUS_PARAM_NAME.equals(ex.getPropertyName())) {
            mapErrors.put(TASK_STATUS_PARAM_NAME, INVALID_STATUS_MESSAGE);
        } else if (DT_UPDATE_FIELD.equals(ex.getPropertyName())) {
            error.add(new ErrorResponse(ErrorType.ERROR, INVALID_UPDATE_DATE_MESSAGE));
        } else {
            error.add(new ErrorResponse(ErrorType.ERROR, REQUEST_INVALID_DATA_MESSAGE));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(mapErrors.isEmpty() ? error : new StructuredErrorResponse(ErrorType.STRUCTURED_ERROR, mapErrors));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handlerNullPointerException(NullPointerException ex) {
        List<ErrorResponse> errors = new ArrayList<>();
        errors.add(new ErrorResponse(ErrorType.ERROR, SERVER_INTERNAL_ERROR_MESSAGE));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }

}
