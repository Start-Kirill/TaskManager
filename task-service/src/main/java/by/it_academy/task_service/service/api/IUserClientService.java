package by.it_academy.task_service.service.api;

import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.dto.UserDto;

import java.util.UUID;

public interface IUserClientService {

    UserDto get(String token, UUID uuid);

    UserDto get(UserDetailsImpl userDetails, UUID uuid);
}
