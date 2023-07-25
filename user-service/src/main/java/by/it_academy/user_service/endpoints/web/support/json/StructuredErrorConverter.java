package by.it_academy.user_service.endpoints.web.support.json;

import by.it_academy.user_service.core.errors.SpecificError;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StructuredErrorConverter extends StdConverter<Map<String, String>, List<SpecificError>> {
    @Override
    public List<SpecificError> convert(Map<String, String> value) {
        List<SpecificError> errors = new ArrayList<>();
        value.forEach((f, m) -> errors.add(new SpecificError(f, m)));
        return errors;
    }
}
