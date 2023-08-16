package by.it_academy.user_service.service.support.spring.converters;

import by.it_academy.task_manager_common.enums.UserRole;
import by.it_academy.task_manager_common.enums.UserStatus;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.core.dto.UserRegistrationDto;
import by.it_academy.user_service.dao.entity.User;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.HashSet;
import java.util.Set;

public class GenericUserCreateDtoConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        HashSet<ConvertiblePair> pairs = new HashSet<>();

        pairs.add(new ConvertiblePair(UserRegistrationDto.class, UserCreateDto.class));
        pairs.add(new ConvertiblePair(User.class, UserCreateDto.class));
        pairs.add(new ConvertiblePair(UserDetailsImpl.class, UserCreateDto.class));


        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() == UserCreateDto.class) {
            return source;
        }

        UserCreateDto userCreateDto = new UserCreateDto();
        if (sourceType.getType() == UserRegistrationDto.class) {
            UserRegistrationDto registrationDto = (UserRegistrationDto) source;

            userCreateDto.setRole(UserRole.USER);
            userCreateDto.setStatus(UserStatus.WAITING_ACTIVATION);

            userCreateDto.setMail(registrationDto.getMail());
            userCreateDto.setFio(registrationDto.getFio());
            userCreateDto.setPassword(registrationDto.getPassword());
        } else if (sourceType.getType() == User.class) {
            User user = (User) source;

            userCreateDto.setPassword(user.getPassword());
            userCreateDto.setRole(user.getRole());
            userCreateDto.setStatus(user.getStatus());
            userCreateDto.setMail(user.getMail());
            userCreateDto.setFio(user.getFio());
        } else {
            UserDetailsImpl userDetails = (UserDetailsImpl) source;

            userCreateDto.setMail(userDetails.getUsername());
            userCreateDto.setFio(userDetails.getFio());
            userCreateDto.setPassword(userCreateDto.getPassword());
            userCreateDto.setStatus(userDetails.getStatus());
            userCreateDto.setRole(userDetails.getRole());
        }

        return userCreateDto;
    }
}
