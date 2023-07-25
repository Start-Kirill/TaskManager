package by.it_academy.user_service.service;

import by.it_academy.user_service.core.dto.ResultOrError;
import by.it_academy.user_service.core.dto.UserLoginDto;
import by.it_academy.user_service.core.dto.UserRegistrationDto;
import by.it_academy.user_service.service.api.IUserAuthenticationService;
import by.it_academy.user_service.service.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {

    private IUserService userService;

    private ConversionService conversionService;

    public UserAuthenticationService(IUserService userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    //    TODO
    @Override
    public ResultOrError signIn(UserRegistrationDto dto) {
        return null;
    }

    //    TODO
    @Override
    public ResultOrError verify(String code, String mail) {
        return null;
    }

    //    TODO
    @Override
    public ResultOrError login(UserLoginDto dto) {
        return null;
    }

    //    TODO
    @Override
    public ResultOrError getMe() {
        return null;
    }
}
