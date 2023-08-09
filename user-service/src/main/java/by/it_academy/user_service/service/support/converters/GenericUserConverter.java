package by.it_academy.user_service.service.support.converters;

import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.dao.entity.User;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.HashSet;
import java.util.Set;

public class GenericUserConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {

        HashSet<ConvertiblePair> pairs = new HashSet<>();

        pairs.add(new ConvertiblePair(UserCreateDto.class, User.class));

        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() == User.class) {
            return source;
        }


        UserCreateDto dto = (UserCreateDto) source;

        User user = new User();

        user.setFio(dto.getFio());
        user.setMail(dto.getMail());
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus());
        user.setPassword(dto.getPassword());

        return user;


    }
}
