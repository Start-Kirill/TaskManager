package by.it_academy.user_service.service.support.spring.converters;

import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.dao.entity.User;
import org.springframework.core.convert.converter.Converter;

public class UserCreateDtoToUserConverter implements Converter<UserCreateDto, User> {

    @Override
    public User convert(UserCreateDto source) {
        User user = new User();

        user.setFio(source.getFio());
        user.setMail(source.getMail());
        user.setRole(source.getRole());
        user.setStatus(source.getStatus());
        user.setPassword(source.getPassword());

        return user;
    }
}
