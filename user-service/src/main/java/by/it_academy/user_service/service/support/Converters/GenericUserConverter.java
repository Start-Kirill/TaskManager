package by.it_academy.user_service.service.support.Converters;

import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.core.dto.UserDto;
import by.it_academy.user_service.dao.entity.User;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GenericUserConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {

        HashSet<ConvertiblePair> pairs = new HashSet<>();

        pairs.add(new ConvertiblePair(UserCreateDto.class, User.class));
        pairs.add(new ConvertiblePair(UserDto.class, User.class));

        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() == User.class) {
            return source;
        }

        if (sourceType.getType() == UserCreateDto.class) {

            UserCreateDto dto = (UserCreateDto) source;

            User user = new User();

            user.setFio(dto.getFio());
            user.setMail(dto.getMail());
            user.setRole(dto.getRole());
            user.setStatus(dto.getStatus());
            user.setPassword(dto.getPassword());
            user.setUuid(UUID.randomUUID());

            LocalDateTime now = LocalDateTime.now();
            user.setDateTimeCreate(now);
            user.setDateTimeCreate(now);

            return user;
        } else {
            UserDto dto = (UserDto) source;

            User user = new User();

            user.setFio(dto.getFio());
            user.setMail(dto.getMail());
            user.setRole(dto.getRole());
            user.setStatus(dto.getStatus());
            user.setPassword(dto.getPassword());
            user.setUuid(dto.getUuid());
            user.setDateTimeCreate(dto.getDateTimeCreate());
            user.setDateTimeUpdate(dto.getDateTimeUpdate());

            return user;
        }

    }
}
