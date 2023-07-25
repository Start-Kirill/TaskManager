package by.it_academy.user_service.endpoints.web.support.json;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class LocalDateTimeToMillisecondsConverter extends StdConverter<LocalDateTime, Long> {

    @Override
    public Long convert(LocalDateTime localDateTime) {
        return ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
