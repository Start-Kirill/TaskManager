package by.it_academy.user_service.service.api;

import by.it_academy.user_service.core.dto.UserLoginDto;
import by.it_academy.user_service.core.dto.UserRegistrationDto;
import by.it_academy.user_service.dao.entity.User;

public interface IUserAuthenticationService {

    void signIn(UserRegistrationDto dto);

    void verify(String code, String mail);

    void login(UserLoginDto dto);

    User getMe();

    void sendCodeAgain(String mail);
}
