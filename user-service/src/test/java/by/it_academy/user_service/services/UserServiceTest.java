package by.it_academy.user_service.services;

import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_manager_common.enums.UserRole;
import by.it_academy.task_manager_common.enums.UserStatus;
import by.it_academy.task_manager_common.exceptions.common.VersionsNotMatchException;
import by.it_academy.task_manager_common.exceptions.structured.NotCorrectPageDataException;
import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.dao.api.IUserDao;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.service.UserHolder;
import by.it_academy.user_service.service.UserService;
import by.it_academy.user_service.service.api.IUserAuditService;
import by.it_academy.user_service.service.exceptions.common.UserNotExistsException;
import by.it_academy.user_service.service.exceptions.structured.MailNotExistsException;
import by.it_academy.user_service.service.exceptions.structured.NotValidUserBodyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final String CORRECT_MAIL = "aaaa@gmail.com";

    private static final String INCORRECT_MAIL = "aaaagmail.com";

    private static final String FIO = "Test User";

    private static final String CORRECT_PASSWORD = "Q1w2e3r4!";

    private static final String INCORRECT_PASSWORD = "123";

    private static final UUID FIRST_USER_UUID = UUID.randomUUID();

    private static final UUID SECOND_USER_UUID = UUID.randomUUID();

    private final List<User> users = generateFakeDBData();

    @Mock
    private IUserDao userDao;

    @Mock
    private ConversionService conversionService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IUserAuditService auditService;

    @Mock
    private UserHolder userHolder;

    @InjectMocks
    private UserService userService;



    @Test
    public void shouldSaveUser() {
        when(userDao.saveAndFlush(Mockito.any(User.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());
        when(passwordEncoder.encode(Mockito.any(CharSequence.class)))
                .thenAnswer(m -> m.getArgument(0, CharSequence.class).toString());
        when(conversionService.convert(Mockito.any(UserCreateDto.class), Mockito.eq(User.class))).thenAnswer(mock -> {

            User user = new User();

            UserCreateDto argument1 = mock.getArgument(0, UserCreateDto.class);

            user.setMail(argument1.getMail());
            user.setFio(argument1.getFio());
            user.setRole(argument1.getRole());
            user.setStatus(argument1.getStatus());
            user.setPassword(argument1.getPassword());

            return user;
        });

        User actual = this.userService.save(generateCorrectUserCreate());

        assertNotNull(actual);
    }

    @Test
    public void shouldThrowWhileSave() {
        assertThrows(NotValidUserBodyException.class, () -> {
            this.userService.save(generateNotCorrectUserCreate());
        });
    }

    @Test
    public void shouldSaveProperly() {
        when(userDao.saveAndFlush(Mockito.any(User.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());
        when(passwordEncoder.encode(Mockito.any(CharSequence.class)))
                .thenAnswer(m -> m.getArgument(0, CharSequence.class).toString());
        when(conversionService.convert(Mockito.any(UserCreateDto.class), Mockito.eq(User.class))).thenAnswer(mock -> {

            User user = new User();

            UserCreateDto argument1 = mock.getArgument(0, UserCreateDto.class);

            user.setMail(argument1.getMail());
            user.setFio(argument1.getFio());
            user.setRole(argument1.getRole());
            user.setStatus(argument1.getStatus());
            user.setPassword(argument1.getPassword());

            return user;
        });

        User save = this.userService.save(generateCorrectUserCreate());

        assertEquals(save.getFio(), FIO);
        assertEquals(save.getMail(), CORRECT_MAIL);
        assertEquals(save.getRole(), UserRole.USER);
        assertEquals(save.getStatus(), UserStatus.ACTIVATED);
        assertEquals(save.getPassword(), CORRECT_PASSWORD);
        assertNotNull(save.getUuid());
        assertNotNull(save.getDateTimeCreate());
        assertNotNull(save.getDateTimeUpdate());
        assertTrue(save.getDateTimeCreate().isBefore(save.getDateTimeUpdate())
                || save.getDateTimeCreate().isEqual(save.getDateTimeUpdate()));
    }

    @Test
    public void shouldSaveByUser() {
        when(userDao.saveAndFlush(Mockito.any(User.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());
        when(passwordEncoder.encode(Mockito.any(CharSequence.class)))
                .thenAnswer(m -> m.getArgument(0, CharSequence.class).toString());
        when(conversionService.convert(Mockito.any(UserCreateDto.class), Mockito.eq(User.class))).thenAnswer(mock -> {

            User user = new User();

            UserCreateDto argument1 = mock.getArgument(0, UserCreateDto.class);

            user.setMail(argument1.getMail());
            user.setFio(argument1.getFio());
            user.setRole(argument1.getRole());
            user.setStatus(argument1.getStatus());
            user.setPassword(argument1.getPassword());

            return user;
        });

        User user = this.userService.saveByUser(generateCorrectUserCreate());

        assertNotNull(user);
    }

    @Test
    public void shouldThrowWhileSaveByUser() {
        assertThrows(NotValidUserBodyException.class, () -> {
            this.userService.saveByUser(generateNotCorrectUserCreate());
        });
    }

    @Test
    public void shouldGetUser() {
        when(userDao.existsById(Mockito.any(UUID.class)))
                .thenAnswer(m -> {
                    UUID uuid = m.getArgument(0, UUID.class);
                    return this.users.stream().anyMatch(u -> u.getUuid().equals(uuid));
                });
        when(userDao.findById(Mockito.any(UUID.class)))
                .thenAnswer(m -> {
                    UUID uuid = m.getArgument(0, UUID.class);
                    return users.stream().filter(u -> u.getUuid().equals(uuid)).findFirst();
                });

        User user = this.userService.get(FIRST_USER_UUID);

        assertEquals(users.get(0), user);
    }

    @Test
    public void shouldThrowWhileGetUser() {
        assertThrows(UserNotExistsException.class, () -> this.userService.get(UUID.randomUUID()));
    }

    @Test
    public void shouldGetPage() {
        when(userDao.findAll(Mockito.any(PageRequest.class)))
                .thenAnswer(m -> new PageImpl<>(users));
        when(conversionService.convert(Mockito.any(Page.class), Mockito.eq(CustomPage.class)))
                .thenAnswer(m -> {
                    CustomPage<User> userCustomPage = new CustomPage<>();
                    userCustomPage.setContent(m.getArgument(0, Page.class).getContent());
                    return userCustomPage;
                });

        CustomPage<User> userCustomPage = userService.get(0, 1);

        assertNotNull(userCustomPage);
    }

    @Test
    public void shouldThrowWhileGetPage() {
        assertThrows(NotCorrectPageDataException.class, () -> {
            userService.get(0, 0);
        });
    }

    @Test
    public void shouldGetPageProperly() {
        when(userDao.findAll(Mockito.any(PageRequest.class)))
                .thenAnswer(m -> new PageImpl<>(users));
        when(conversionService.convert(Mockito.any(Page.class), Mockito.eq(CustomPage.class)))
                .thenAnswer(m -> {
                    CustomPage<User> userCustomPage = new CustomPage<>();
                    userCustomPage.setContent(m.getArgument(0, Page.class).getContent());
                    return userCustomPage;
                });

        CustomPage<User> userCustomPage = this.userService.get(0, 1);

        assertEquals(userCustomPage.getContent(), users);
        assertEquals(userCustomPage.getSize(), 1);
        assertEquals(userCustomPage.getNumber(), 0);
    }

    @Test
    public void shouldFindAllByUuids() {
        List<UUID> willingUsers = List.of(FIRST_USER_UUID, SECOND_USER_UUID);

        assertNotNull(userService.findAllByUuid(willingUsers));
    }

    @Test
    public void shouldGetEmptyWhileFindAllByUuids() {
        List<UUID> willingUsers = List.of(UUID.randomUUID());

        assertTrue(userService.findAllByUuid(willingUsers).isEmpty());
    }

    @Test
    public void shouldFindAllByUuidsProperly() {
        when(this.userDao.findAllByUuidIn(Mockito.any(Collection.class)))
                .thenAnswer(m -> {
                    Collection<UUID> willingUsers = (Collection<UUID>) m.getArgument(0, Collection.class);
                    return users.stream().filter(u -> willingUsers.contains(u.getUuid())).toList();
                });

        List<UUID> willingUsers = List.of(FIRST_USER_UUID, SECOND_USER_UUID);

        assertEquals(users, userService.findAllByUuid(willingUsers));
    }

    @Test
    public void shouldFindByMail() {
        when(this.userDao.existsByMail(Mockito.any(String.class)))
                .thenAnswer(m -> {
                    String mail = m.getArgument(0, String.class);
                    return users.stream().anyMatch(u -> u.getMail().equals(mail));
                });
        when(this.userDao.findByMail(Mockito.any(String.class)))
                .thenAnswer(m -> {
                    String mail = m.getArgument(0, String.class);
                    return users.stream().filter(u -> u.getMail().equals(mail)).findFirst();
                });

        User user = this.userService.findByMail(CORRECT_MAIL);

        assertNotNull(user);
    }

    @Test
    public void shouldThrowWhileFindByMail() {
        assertThrows(MailNotExistsException.class, () -> {
            this.userService.findByMail(INCORRECT_MAIL);
        });
    }

    @Test
    public void shouldFindByMailProperly() {
        when(this.userDao.existsByMail(Mockito.any(String.class)))
                .thenAnswer(m -> {
                    String mail = m.getArgument(0, String.class);
                    return users.stream().anyMatch(u -> u.getMail().equals(mail));
                });
        when(this.userDao.findByMail(Mockito.any(String.class)))
                .thenAnswer(m -> {
                    String mail = m.getArgument(0, String.class);
                    return users.stream().filter(u -> u.getMail().equals(mail)).findFirst();
                });

        User user = this.userService.findByMail(CORRECT_MAIL);

        assertEquals(CORRECT_MAIL, user.getMail());
    }


    @Test
    public void shouldUpdate() {
        when(userDao.existsById(Mockito.any(UUID.class)))
                .thenAnswer(m -> {
                    UUID uuid = m.getArgument(0, UUID.class);
                    return this.users.stream().anyMatch(u -> u.getUuid().equals(uuid));
                });

        when(userDao.findById(Mockito.any(UUID.class)))
                .thenAnswer(m -> {
                    UUID uuid = m.getArgument(0, UUID.class);
                    return users.stream().filter(u -> u.getUuid().equals(uuid)).findFirst();
                });
        when(userDao.saveAndFlush(Mockito.any(User.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());
        when(passwordEncoder.encode(Mockito.any(CharSequence.class)))
                .thenAnswer(m -> m.getArgument(0, CharSequence.class).toString());

        UserCreateDto correctUserCreateDto = generateCorrectUserCreate();
        LocalDateTime realVersion = this.users.get(0).getDateTimeUpdate();
        User user = this.userService.update(
                correctUserCreateDto,
                FIRST_USER_UUID,
                realVersion);

        assertNotNull(user);
    }

    @Test
    public void shouldThrowWhileUpdate() {
        when(userDao.existsById(Mockito.any(UUID.class)))
                .thenAnswer(m -> {
                    UUID uuid = m.getArgument(0, UUID.class);
                    return this.users.stream().anyMatch(u -> u.getUuid().equals(uuid));
                });
        when(userDao.findById(Mockito.any(UUID.class)))
                .thenAnswer(m -> {
                    UUID uuid = m.getArgument(0, UUID.class);
                    return users.stream().filter(u -> u.getUuid().equals(uuid)).findFirst();
                });

        UserCreateDto correctUserCreateDto = generateCorrectUserCreate();
        LocalDateTime wrongVersion = LocalDateTime.now();

        assertThrows(VersionsNotMatchException.class, () -> {
            this.userService.update(
                    correctUserCreateDto,
                    FIRST_USER_UUID,
                    wrongVersion
            );
        });
    }

    @Test
    public void shouldUpdateProperly() {
        when(userDao.existsById(Mockito.any(UUID.class)))
                .thenAnswer(m -> {
                    UUID uuid = m.getArgument(0, UUID.class);
                    return this.users.stream().anyMatch(u -> u.getUuid().equals(uuid));
                });

        when(userDao.findById(Mockito.any(UUID.class)))
                .thenAnswer(m -> {
                    UUID uuid = m.getArgument(0, UUID.class);
                    return users.stream().filter(u -> u.getUuid().equals(uuid)).findFirst();
                });
        when(userDao.saveAndFlush(Mockito.any(User.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());
        when(passwordEncoder.encode(Mockito.any(CharSequence.class)))
                .thenAnswer(m -> m.getArgument(0, CharSequence.class).toString());

        UserCreateDto userCreateDto = generateCorrectUserCreate();
        LocalDateTime dateTimeUpdate = this.users.get(0).getDateTimeUpdate();
        LocalDateTime dateTimeCreate = this.users.get(0).getDateTimeCreate();
        User update = this.userService.update(userCreateDto, FIRST_USER_UUID, dateTimeUpdate);

        assertEquals(userCreateDto.getFio(), update.getFio());
        assertEquals(userCreateDto.getMail(), update.getMail());
        assertEquals(userCreateDto.getPassword(), update.getPassword());
        assertEquals(userCreateDto.getRole(), update.getRole());
        assertEquals(userCreateDto.getStatus(), update.getStatus());
        assertEquals(dateTimeCreate, update.getDateTimeCreate());
        assertEquals(dateTimeUpdate, update.getDateTimeUpdate());
        assertEquals(FIRST_USER_UUID, update.getUuid());
    }


    private UserCreateDto generateCorrectUserCreate() {
        return new UserCreateDto(
                CORRECT_MAIL,
                FIO,
                UserRole.USER,
                UserStatus.ACTIVATED,
                CORRECT_PASSWORD
        );
    }

    private UserCreateDto generateNotCorrectUserCreate() {
        return new UserCreateDto(
                INCORRECT_MAIL,
                FIO,
                UserRole.USER,
                UserStatus.ACTIVATED,
                INCORRECT_PASSWORD
        );
    }


    private List<User> generateFakeDBData() {
        List<User> users = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();

        users.add(new User(
                FIRST_USER_UUID,
                now,
                now,
                CORRECT_MAIL,
                FIO,
                UserRole.USER,
                UserStatus.ACTIVATED,
                CORRECT_PASSWORD
        ));

        now = LocalDateTime.now();

        users.add(new User(
                SECOND_USER_UUID,
                now,
                now,
                CORRECT_MAIL,
                FIO,
                UserRole.USER,
                UserStatus.ACTIVATED,
                CORRECT_PASSWORD
        ));
        return users;
    }


}
