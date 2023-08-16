package by.it_academy.user_service.endpoints.web.support.spring.converters;

import by.it_academy.task_manager_common.dto.UserDto;
import by.it_academy.user_service.dao.entity.User;
import org.springframework.core.convert.converter.Converter;

public class UserToUserDtoConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User source) {
        UserDto userDto = new UserDto();

        userDto.setUuid(source.getUuid());
        userDto.setDateTimeCreate(source.getDateTimeCreate());
        userDto.setDateTimeUpdate(source.getDateTimeUpdate());
        userDto.setMail(source.getMail());
        userDto.setFio(source.getFio());
        userDto.setRole(source.getRole());
        userDto.setStatus(source.getStatus());

        return userDto;
    }
}
