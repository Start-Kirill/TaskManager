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
import org.junit.jupiter.api.Assertions;
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

    @BeforeEach
    public void setUp() {
        Mockito.lenient().when(userDao.saveAndFlush(Mockito.any(User.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        Mockito.lenient().when(passwordEncoder.encode(Mockito.any(CharSequence.class)))
                .thenAnswer(m -> m.getArgument(0, CharSequence.class).toString());


        Mockito.lenient().when(conversionService.convert(Mockito.any(UserCreateDto.class), Mockito.eq(User.class))).thenAnswer(mock -> {

            User user = new User();

            UserCreateDto argument1 = mock.getArgument(0, UserCreateDto.class);

            user.setMail(argument1.getMail());
            user.setFio(argument1.getFio());
            user.setRole(argument1.getRole());
            user.setStatus(argument1.getStatus());
            user.setPassword(argument1.getPassword());

            return user;
        });

        Mockito.lenient().when(userDao.existsById(Mockito.any(UUID.class)))
                .thenAnswer(m -> {
                    UUID uuid = m.getArgument(0, UUID.class);
                    return this.users.stream().anyMatch(u -> u.getUuid().equals(uuid));
                });

        Mockito.lenient().when(userDao.findById(Mockito.any(UUID.class)))
                .thenAnswer(m -> {
                    UUID uuid = m.getArgument(0, UUID.class);
                    return users.stream().filter(u -> u.getUuid().equals(uuid)).findFirst();
                });

        Mockito.lenient().when(userDao.findAll(Mockito.any(PageRequest.class)))
                .thenAnswer(m -> new PageImpl<>(users));

        Mockito.lenient().when(conversionService.convert(Mockito.any(Page.class), Mockito.eq(CustomPage.class)))
                .thenAnswer(m -> {
                    CustomPage<User> userCustomPage = new CustomPage<>();
                    userCustomPage.setContent(m.getArgument(0, Page.class).getContent());
                    return userCustomPage;
                });
        Mockito.lenient().when(this.userDao.existsByMail(Mockito.any(String.class)))
                .thenAnswer(m -> {
                    String mail = m.getArgument(0, String.class);
                    return users.stream().anyMatch(u -> u.getMail().equals(mail));
                });
        Mockito.lenient().when(this.userDao.findByMail(Mockito.any(String.class)))
                .thenAnswer(m -> {
                    String mail = m.getArgument(0, String.class);
                    return users.stream().filter(u -> u.getMail().equals(mail)).findFirst();
                });
        Mockito.lenient().when(this.userDao.findAllByUuidIn(Mockito.any(Collection.class)))
                .thenAnswer(m -> {
                    Collection<UUID> willingUsers = (Collection<UUID>) m.getArgument(0, Collection.class);
                    return users.stream().filter(u -> willingUsers.contains(u.getUuid())).toList();
                });
    }

    @Test
    public void shouldSaveUser() {
        Assertions.assertNotNull(this.userService.save(generateCorrectUserCreate()));
    }

    @Test
    public void shouldThrowWhileSave() {
        Assertions.assertThrows(NotValidUserBodyException.class, () -> {
            this.userService.save(generateNotCorrectUserCreate());
        });
    }

    @Test
    public void shouldSaveProperly() {
        User save = this.userService.save(generateCorrectUserCreate());
        Assertions.assertEquals(save.getFio(), FIO);
        Assertions.assertEquals(save.getMail(), CORRECT_MAIL);
        Assertions.assertEquals(save.getRole(), UserRole.USER);
        Assertions.assertEquals(save.getStatus(), UserStatus.ACTIVATED);
        Assertions.assertEquals(save.getPassword(), CORRECT_PASSWORD);
        Assertions.assertNotNull(save.getUuid());
        Assertions.assertNotNull(save.getDateTimeCreate());
        Assertions.assertNotNull(save.getDateTimeUpdate());
        Assertions.assertTrue(save.getDateTimeCreate().isBefore(save.getDateTimeUpdate())
                || save.getDateTimeCreate().isEqual(save.getDateTimeUpdate()));
    }

    @Test
    public void shouldSaveByUser() {
        Assertions.assertNotNull(this.userService.saveByUser(generateCorrectUserCreate()));
    }

    @Test
    public void shouldThrowWhileSaveByUser() {
        Assertions.assertThrows(NotValidUserBodyException.class, () -> {
            this.userService.saveByUser(generateNotCorrectUserCreate());
        });
    }

    @Test
    public void shouldGetUser() {
        User user = this.userService.get(FIRST_USER_UUID);
        Assertions.assertEquals(users.get(0), user);
    }

    @Test
    public void shouldThrowWhileGetUser() {
        Assertions.assertThrows(UserNotExistsException.class, () -> this.userService.get(UUID.randomUUID()));
    }

    @Test
    public void shouldGetPage() {
        Assertions.assertNotNull(userService.get(0, 1));
    }

    @Test
    public void shouldThrowWhileGetPage() {
        Assertions.assertThrows(NotCorrectPageDataException.class, () -> {
            userService.get(0, 0);
        });
    }

    @Test
    public void shouldGetPageProperly() {
        CustomPage<User> userCustomPage = this.userService.get(0, 1);
        Assertions.assertEquals(userCustomPage.getContent(), users);
        Assertions.assertEquals(userCustomPage.getSize(), 1);
        Assertions.assertEquals(userCustomPage.getNumber(), 0);
    }

    @Test
    public void shouldFindAllByUuids() {
        List<UUID> willingUsers = List.of(FIRST_USER_UUID, SECOND_USER_UUID);
        Assertions.assertNotNull(userService.findAllByUuid(willingUsers));
    }

    @Test
    public void shouldGetEmptyWhileFindAllByUuids() {
        List<UUID> willingUsers = List.of(UUID.randomUUID());
        Assertions.assertTrue(userService.findAllByUuid(willingUsers).isEmpty());
    }

    @Test
    public void shouldFindAllByUuidsProperly() {
        List<UUID> willingUsers = List.of(FIRST_USER_UUID, SECOND_USER_UUID);
        Assertions.assertEquals(users, userService.findAllByUuid(willingUsers));
    }

    @Test
    public void shouldFindByMail() {
        Assertions.assertNotNull(this.userService.findByMail(CORRECT_MAIL));
    }

    @Test
    public void shouldThrowWhileFindByMail() {
        Assertions.assertThrows(MailNotExistsException.class, () -> {
            this.userService.findByMail(INCORRECT_MAIL);
        });
    }

    @Test
    public void shouldFindByMailProperly() {
        Assertions.assertEquals(CORRECT_MAIL, this.userService.findByMail(CORRECT_MAIL).getMail());
    }


    @Test
    public void shouldUpdate() {
        UserCreateDto correctUserCreateDto = generateCorrectUserCreate();
        LocalDateTime realVersion = this.users.get(0).getDateTimeUpdate();
        Assertions.assertNotNull(this.userService.update(
                correctUserCreateDto,
                FIRST_USER_UUID,
                realVersion));
    }

    @Test
    public void shouldThrowWhileUpdate() {
        UserCreateDto correctUserCreateDto = generateCorrectUserCreate();
        LocalDateTime wrongVersion = LocalDateTime.now();
        Assertions.assertThrows(VersionsNotMatchException.class, () -> {
            this.userService.update(
                    correctUserCreateDto,
                    FIRST_USER_UUID,
                    wrongVersion
            );
        });
    }

    @Test
    public void shouldUpdateProperly() {
        UserCreateDto userCreateDto = generateCorrectUserCreate();
        LocalDateTime dateTimeUpdate = this.users.get(0).getDateTimeUpdate();
        LocalDateTime dateTimeCreate = this.users.get(0).getDateTimeCreate();
        User update = this.userService.update(userCreateDto, FIRST_USER_UUID, dateTimeUpdate);
        Assertions.assertEquals(userCreateDto.getFio(), update.getFio());
        Assertions.assertEquals(userCreateDto.getMail(), update.getMail());
        Assertions.assertEquals(userCreateDto.getPassword(), update.getPassword());
        Assertions.assertEquals(userCreateDto.getRole(), update.getRole());
        Assertions.assertEquals(userCreateDto.getStatus(), update.getStatus());
        Assertions.assertEquals(dateTimeCreate, update.getDateTimeCreate());
        Assertions.assertEquals(dateTimeUpdate, update.getDateTimeUpdate());
        Assertions.assertEquals(FIRST_USER_UUID, update.getUuid());
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
