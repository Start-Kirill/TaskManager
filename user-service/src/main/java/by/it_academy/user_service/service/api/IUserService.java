package by.it_academy.user_service.service.api;

import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.dao.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface IUserService extends ICRUDService<User, UserCreateDto> {

    User findByMail(String mail);

    boolean existsByMail(String mail);

    User saveByUser(UserCreateDto dto);

    List<User> findAllByUuid(Collection<UUID> users);

}
