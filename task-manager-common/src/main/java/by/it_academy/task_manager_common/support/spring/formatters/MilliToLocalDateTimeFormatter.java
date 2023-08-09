package by.it_academy.task_manager_common.support.spring.formatters;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.exceptions.common.NotCorrectValueException;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MilliToLocalDateTimeFormatter implements Formatter<LocalDateTime> {


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
