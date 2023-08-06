package by.it_academy.user_service.service.api;

import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.task_manager_common.entity.User;

public interface IUserService extends ICRUDService<User, UserCreateDto> {

    User findByMail(String mail);

    boolean existsByMail(String mail);

    User auditedSave(UserCreateDto dto);
}
