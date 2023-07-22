package by.it_academy.user_service.service;

import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.core.enums.UserRole;
import by.it_academy.user_service.core.enums.UserStatus;
import by.it_academy.user_service.service.api.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

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

    @Test
    public void checkOptimisticLockingFailureException() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setStatus(UserStatus.ACTIVATED);
        userCreateDto.setMail("locking@test2.com");
        userCreateDto.setFio("Locking test2");
        userCreateDto.setPassword("Q1w2e3r4!");
        userCreateDto.setRole(UserRole.USER);
        UUID uuid = UUID.fromString("ac53a014-8cad-49b5-b4f2-2bb10e62e85b");
        Assertions.assertNotNull(userService.update(userCreateDto, uuid, 1L));
    }
}
