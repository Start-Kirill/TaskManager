package by.it_academy.user_service.endpoints.web.support.converters;

import by.it_academy.task_manager_common.dto.UserDto;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.entity.User;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.HashSet;
import java.util.Set;

public class GenericUserDtoConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        HashSet<ConvertiblePair> pairs = new HashSet<>();

        pairs.add(new ConvertiblePair(User.class, UserDto.class));
        pairs.add(new ConvertiblePair(UserDetailsImpl.class, UserDto.class));

        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() == UserDto.class) {
            return source;
        }

        UserDto userDto = new UserDto();

        if (sourceType.getType() == User.class) {
            User user = (User) source;

            userDto.setUuid(user.getUuid());
            userDto.setDateTimeCreate(user.getDateTimeCreate());
            userDto.setDateTimeUpdate(user.getDateTimeUpdate());
            userDto.setMail(user.getMail());
            userDto.setFio(user.getFio());
            userDto.setRole(user.getRole());
            userDto.setStatus(user.getStatus());
        }

        return userDto;
    }
}
