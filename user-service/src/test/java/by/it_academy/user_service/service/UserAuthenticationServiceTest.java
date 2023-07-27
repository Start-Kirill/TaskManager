package by.it_academy.user_service.service;

import by.it_academy.user_service.core.dto.UserRegistrationDto;
import by.it_academy.user_service.service.api.IUserAuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserAuthenticationServiceTest {

    @Autowired
    IUserAuthenticationService authenticationService;

    @Test
    public void testSendEmail() {
        UserRegistrationDto registrationDto = new UserRegistrationDto("kirunya1993@gmail.com", "KS", "Q1w2e3r4!");
        authenticationService.signIn(registrationDto);
    }
}
