package by.it_academy.user_service.services;


import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.core.dto.UserLoginDto;
import by.it_academy.user_service.core.dto.UserRegistrationDto;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.dao.entity.Verification;
import by.it_academy.user_service.service.UserAuthenticationService;
import by.it_academy.user_service.service.UserHolder;
import by.it_academy.user_service.service.api.IUserAuditService;
import by.it_academy.user_service.service.api.IUserService;
import by.it_academy.user_service.service.api.IVerificationService;
import by.it_academy.user_service.service.exceptions.common.MailMissingException;
import by.it_academy.user_service.service.exceptions.common.NotVerifyUserException;
import by.it_academy.user_service.service.exceptions.structured.NotValidUserBodyException;
import by.it_academy.user_service.utils.JwtTokenHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserAuthenticationServiceTest {

    private static final String MAIL = "test@mail.ru";

    private static final String FIO = "Test";

    private static final String PASSWORD = "Q1w2e3r4!";

    private static final String CODE = "123456";

    private static final String TOKEN = "Token";

    @Mock
    private IUserService userService;

    @Mock
    private IUserAuditService auditService;

    @Mock
    private IVerificationService verificationCodeService;

    @Mock
    private ConversionService conversionService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenHandler tokenHandler;

    @Mock
    private UserHolder userHolder;

    @InjectMocks
    private UserAuthenticationService userAuthenticationService;

    @Test
    public void shouldSignIn() {
        final UserCreateDto userCreateDto = mock(UserCreateDto.class);
        when(this.conversionService.convert(any(UserRegistrationDto.class), eq(UserCreateDto.class))).thenReturn(userCreateDto);
        final User user = mock(User.class);
        when(this.userService.save(any(UserCreateDto.class))).thenReturn(user);

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(MAIL, FIO, PASSWORD);

        assertDoesNotThrow(() -> this.userAuthenticationService.signIn(userRegistrationDto));
    }

    @Test
    public void shouldThrowWhileSignIn() {
        UserRegistrationDto userRegistrationDto = mock(UserRegistrationDto.class);

        assertThrows(NotValidUserBodyException.class, () -> this.userAuthenticationService.signIn(userRegistrationDto));
    }

    @Test
    public void shouldVerify() {
        final User user = mock(User.class);
        when(this.userService.findByMail(anyString())).thenReturn(user);
        final Verification verification = mock(Verification.class);
        when(verification.getCode()).thenReturn(CODE);
        when(this.verificationCodeService.get(any(User.class))).thenReturn(verification);

        assertDoesNotThrow(() -> this.userAuthenticationService.verify(CODE, MAIL));
    }

    @Test
    public void shouldThrowWhileVerify() {
        final User user = mock(User.class);
        when(this.userService.findByMail(anyString())).thenReturn(user);
        final Verification verification = mock(Verification.class);
        when(this.verificationCodeService.get(any(User.class))).thenReturn(verification);

        assertThrows(MailMissingException.class, () -> this.userAuthenticationService.verify(CODE, ""));
        assertThrows(NotVerifyUserException.class, () -> this.userAuthenticationService.verify(CODE, MAIL));
    }

    @Test
    public void shouldLogin() {
        final User user = mock(User.class);
        when(user.getPassword()).thenReturn(PASSWORD);
        when(this.userService.findByMail(anyString())).thenReturn(user);
        when(this.passwordEncoder.matches(anyString(), anyString()))
                .then(mock -> mock.getArgument(0, String.class).equals(mock.getArgument(1, String.class)));
        when(this.tokenHandler.generateAccessToken(any(), any(), any(), any())).thenReturn(TOKEN);
        final UserLoginDto userLoginDto = mock(UserLoginDto.class);
        when(userLoginDto.getMail()).thenReturn(MAIL);
        when(userLoginDto.getPassword()).thenReturn(PASSWORD);

        String actual = this.userAuthenticationService.login(userLoginDto);

        assertNotNull(actual);
        assertEquals(TOKEN, actual);
    }

    @Test
    public void shouldThrowWhileLogin() {
        final UserLoginDto userLoginDto = mock(UserLoginDto.class);

        assertThrows(NotValidUserBodyException.class, () -> this.userAuthenticationService.login(userLoginDto));
    }

    @Test
    public void shouldGetMe() {
        final UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(this.userHolder.getUser()).thenReturn(userDetails);
        when(userDetails.getUuid()).thenReturn(mock(UUID.class));
        final User user = mock(User.class);
        when(this.userService.get(any(UUID.class))).thenReturn(user);

        User actual = this.userAuthenticationService.getMe();

        assertNotNull(actual);
        assertEquals(actual, user);
    }

    @Test
    public void shouldSendCodeAgain() {
        final User user = mock(User.class);
        when(this.userService.findByMail(anyString())).thenReturn(user);
        final Verification verification = mock(Verification.class);
        when(this.verificationCodeService.get(any(User.class))).thenReturn(verification);

        assertDoesNotThrow(() -> this.userAuthenticationService.sendCodeAgain(MAIL));
    }

}
