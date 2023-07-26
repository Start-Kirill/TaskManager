package by.it_academy.user_service.endpoints.web.support.converters;

import by.it_academy.user_service.core.enums.ErrorType;
import by.it_academy.user_service.core.errors.ErrorResponse;
import by.it_academy.user_service.endpoints.web.exceptions.NotCorrectValueException;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocalDateTimeToMilliFormatter implements Formatter<LocalDateTime> {


    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        try {
            long milliseconds = Long.parseLong(text);
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
        } catch (NumberFormatException ex) {
            List<ErrorResponse> errorResponses = new ArrayList<>();
            errorResponses.add(new ErrorResponse(ErrorType.ERROR, "Update date contains not correct characters. Must be digits"));
            throw new NotCorrectValueException(errorResponses);
        }
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return object.toString();
    }
}
