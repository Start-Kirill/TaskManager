package by.it_academy.user_service.service.support.converters;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

public class GenericMillisecondConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        HashSet<ConvertiblePair> pairs = new HashSet<>();

        pairs.add(new ConvertiblePair(LocalDateTime.class, Long.class));

        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() == Long.class) {
            return source;
        }

        LocalDateTime dateTime = (LocalDateTime) source;
        Long milli = ZonedDateTime.of(dateTime, ZoneId.systemDefault()).toInstant().toEpochMilli();

        return milli;
    }
}
