package by.it_academy.user_service.service.api;

import by.it_academy.user_service.core.dto.ResultOrError;
import by.it_academy.user_service.core.dto.UserLoginDto;
import by.it_academy.user_service.core.dto.UserRegistrationDto;

public interface IUserAuthenticationService {

    ResultOrError signIn(UserRegistrationDto dto);

    ResultOrError verify(String code, String mail);

    ResultOrError login(UserLoginDto dto);

    ResultOrError getMe();
}
