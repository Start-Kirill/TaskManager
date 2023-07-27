package by.it_academy.user_service.service.api;

import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.dao.entity.User;

import java.util.Optional;

public interface IUserService extends ICRUDService<User, UserCreateDto> {

    User findByMail(String mail);


}
