package by.it_academy.user_service.dao.api;

import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.core.enums.UserRole;
import by.it_academy.user_service.core.enums.UserStatus;
import by.it_academy.user_service.dao.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
public class TestDao {

    @Autowired
    private IUserDao userDao;

    @Test
    public void uniqueUuidTest() {
        User user = new User();
        user.setUuid(UUID.fromString("ac53a014-8cad-49b5-b4f2-2bb10e62e85b"));
        user.setStatus(UserStatus.ACTIVATED);
        user.setRole(UserRole.USER);
        user.setFio("Ks");
        user.setMail("useruuidtest@one.com");
        user.setPassword("Q1w2e3r4!");
        LocalDateTime now = LocalDateTime.now();
        user.setDateTimeCreate(now);
        user.setDateTimeUpdate(now);
        Assertions.assertNotNull(userDao.save(user));
    }

}
