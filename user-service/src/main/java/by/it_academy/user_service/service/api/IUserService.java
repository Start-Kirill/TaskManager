package by.it_academy.user_service.service.api;

import by.it_academy.user_service.core.dto.ResultOrError;
import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.dao.entity.User;

public interface IUserService extends ICRUDService<ResultOrError, UserCreateDto> {
}
