package by.it_academy.user_service.service;

import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.core.enums.UserRole;
import by.it_academy.user_service.core.enums.UserStatus;
import by.it_academy.user_service.service.api.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    void saveUserTest() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setFio("");
        userCreateDto.setMail("test@mail.ru");
        userCreateDto.setRole(UserRole.USER);
        userCreateDto.setPassword("q1w2e3r4");
        userCreateDto.setStatus(UserStatus.ACTIVATED);
        Assertions.assertNotNull(this.userService.save(userCreateDto));
    }
}
