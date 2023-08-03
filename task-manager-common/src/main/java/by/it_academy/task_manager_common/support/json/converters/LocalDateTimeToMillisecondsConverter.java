package by.it_academy.task_manager_common.support.json.converters;

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
