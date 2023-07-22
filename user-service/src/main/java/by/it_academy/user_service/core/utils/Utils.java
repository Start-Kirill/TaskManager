package by.it_academy.user_service.core.utils;

import by.it_academy.user_service.core.enums.ErrorType;
import by.it_academy.user_service.core.errors.SpecificError;
import by.it_academy.user_service.core.errors.StructuredErrorResponse;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static StructuredErrorResponse makeStructuredError(List<SpecificError> specificErrors) {
        StructuredErrorResponse structuredErrorResponse = new StructuredErrorResponse();
        structuredErrorResponse.setErrors(specificErrors);
        structuredErrorResponse.setLogref(ErrorType.STRUCTURED_ERROR);
        return structuredErrorResponse;
    }

    public static StructuredErrorResponse makeStructuredError(String message, String field) {
        List<SpecificError> specificErrors = new ArrayList<>();
        specificErrors.add(new SpecificError(message, field));
        return makeStructuredError(specificErrors);
    }
}
