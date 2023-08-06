package by.it_academy.user_service.service.api;

import by.it_academy.user_service.core.dto.UserLoginDto;
import by.it_academy.user_service.core.dto.UserRegistrationDto;
import by.it_academy.task_manager_common.entity.User;

import java.util.UUID;

public interface IUserAuthenticationService {

    void signIn(UserRegistrationDto dto);

    void verify(String code, String mail);

    String login(UserLoginDto dto);

    User getMe(UUID uuid);

    void sendCodeAgain(String mail);
}
