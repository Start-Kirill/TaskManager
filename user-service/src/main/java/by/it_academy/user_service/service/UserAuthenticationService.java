package by.it_academy.user_service.service;

import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.enums.UserStatus;
import by.it_academy.user_service.config.property.AppProperty;
import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.core.dto.UserLoginDto;
import by.it_academy.user_service.core.dto.UserRegistrationDto;
import by.it_academy.user_service.core.dto.VerificationCodeCreateDto;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.dao.entity.VerificationCode;
import by.it_academy.user_service.service.api.*;
import by.it_academy.user_service.service.exceptions.common.DeactivatedUserException;
import by.it_academy.user_service.service.exceptions.common.NotVerifyUserException;
import by.it_academy.user_service.service.exceptions.structured.MailNotExistsException;
import by.it_academy.user_service.service.exceptions.structured.NotCorrectPasswordException;
import by.it_academy.user_service.service.exceptions.structured.NotValidUserBodyException;
import by.it_academy.user_service.utils.JwtTokenHandler;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {

    private static final String PASSWORD_FIELD_NAME = "password";

    private static final String MAIL_FIELD_NAME = "mail";

    private static final String FIO_FIELD_NAME = "fio";

    private final IUserService userService;

    private final IUserAuditService auditService;

    private final IVerificationCodeService verificationCodeService;

    private final ConversionService conversionService;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenHandler tokenHandler;

    private final AppProperty.Verification verification;

    private final UserHolder userHolder;

    private final INotificationService mailSenderService;


    public UserAuthenticationService(IUserService userService,
                                     ConversionService conversionService,
                                     AppProperty property,
                                     IVerificationCodeService verificationCodeService,
                                     PasswordEncoder passwordEncoder,
                                     JwtTokenHandler tokenHandler,
                                     IUserAuditService auditService,
                                     UserHolder userHolder,
                                     INotificationService mailSenderService) {
        this.userService = userService;
        this.conversionService = conversionService;
        this.verification = property.getVerification();
        this.verificationCodeService = verificationCodeService;
        this.passwordEncoder = passwordEncoder;
        this.tokenHandler = tokenHandler;
        this.auditService = auditService;
        this.userHolder = userHolder;
        this.mailSenderService = mailSenderService;
    }

    @Transactional
    @Override
    public void signIn(UserRegistrationDto dto) {

        validate(dto);

        UserCreateDto userCreateDto = this.conversionService.convert(dto, UserCreateDto.class);
        User user = this.userService.save(userCreateDto);

        String code = this.verificationCodeService.generateCode();
        VerificationCodeCreateDto verificationCodeCreateDto = new VerificationCodeCreateDto(code, user);
        this.verificationCodeService.save(verificationCodeCreateDto);
        sendVerificationCode(dto.getMail());

        this.auditService.saveBySystem(user.getUuid(), "User was registered");
    }

    @Transactional
    @Override
    public void verify(String code, String mail) {

        List<ErrorResponse> errors = new ArrayList<>();

        User user = this.userService.findByMail(mail);
        VerificationCode verificationCode = this.verificationCodeService.getByUser(user);
        if (code != null) {
            if (code.equals(verificationCode.getCode())) {
                user.setStatus(UserStatus.ACTIVATED);
            } else {
                errors.add(new ErrorResponse(ErrorType.ERROR, "Incorrect verification code. Check it or send the code again"));
            }
        } else {
            errors.add(new ErrorResponse(ErrorType.ERROR, "Verification code is missing"));
        }

        if (!errors.isEmpty()) {
            throw new NotVerifyUserException(errors);
        }

        this.verificationCodeService.delete(verificationCode.getUuid(), verificationCode.getDtUpdate());

        UserDetailsImpl userDetails = this.conversionService.convert(user, UserDetailsImpl.class);
        this.auditService.save(userDetails, user.getUuid(), "User was activated");
    }


    @Override
    public String login(UserLoginDto dto) {
        validate(dto);

        Map<String, String> errors = new HashMap<>();
        User user = null;
        try {
            user = this.userService.findByMail(dto.getMail());
            if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                errors.put(PASSWORD_FIELD_NAME, "Probably password is not correct");
                errors.put(MAIL_FIELD_NAME, "Probably email is not correct");
            }
        } catch (MailNotExistsException ex) {
            errors.put(PASSWORD_FIELD_NAME, "Probably password is not correct");
            errors.put(MAIL_FIELD_NAME, "Probably email is not correct");
        }

        if (!errors.isEmpty()) {
            throw new NotCorrectPasswordException(errors);
        }

        UserStatus status = user.getStatus();
        if (UserStatus.DEACTIVATED.equals(status)) {
            throw new DeactivatedUserException(List.of(new ErrorResponse(ErrorType.ERROR, "Such user was blocked or deleted")));
        }

        String token = this.tokenHandler.generateAccessToken(user.getUuid(), user.getMail(), user.getFio(), user.getRole());

        UserDetailsImpl userDetails = this.conversionService.convert(user, UserDetailsImpl.class);
        this.auditService.save(userDetails, user.getUuid(), "User was logged in");

        return token;
    }


    @Transactional(readOnly = true)
    @Override
    public User getMe() {
        UserDetailsImpl user = this.userHolder.getUser();
        return this.userService.get(user.getUuid());
    }

    @Transactional
    @Override
    public void sendCodeAgain(String mail) {
        User user = this.userService.findByMail(mail);

        String code = this.verificationCodeService.generateCode();
        VerificationCode verificationCode = this.verificationCodeService.getByUser(user);
        VerificationCodeCreateDto codeCreateDto = new VerificationCodeCreateDto(code, user);
        this.verificationCodeService.update(codeCreateDto, verificationCode.getUuid(), verificationCode.getDtUpdate());

        sendVerificationCode(mail);

        this.auditService.saveBySystem(user.getUuid(), "Verification code was sent again");
    }

    private void validate(UserRegistrationDto dto) {

        Map<String, String> errors = new HashMap<>();

        String mail = dto.getMail();
        if (mail == null) {
            errors.put(MAIL_FIELD_NAME, "Mail is missing");
        } else if ("".equals(mail)) {
            errors.put(MAIL_FIELD_NAME, "Mail must not to be empty");
        }

        String fio = dto.getFio();
        if (fio == null) {
            errors.put(FIO_FIELD_NAME, "Fio is missing");
        } else if ("".equals(fio)) {
            errors.put(FIO_FIELD_NAME, "Fio must not to be empty");
        }

        String password = dto.getPassword();
        if (password == null) {
            errors.put(PASSWORD_FIELD_NAME, "Password is missing");
        } else if ("".equals(password)) {
            errors.put(PASSWORD_FIELD_NAME, "Password must not to be empty");
        }

        if (!errors.isEmpty()) {
            throw new NotValidUserBodyException(errors);
        }
    }

    private void validate(UserLoginDto dto) {
        Map<String, String> errors = new HashMap<>();

        String mail = dto.getMail();

        if (mail == null) {
            errors.put(MAIL_FIELD_NAME, "Mail is missing");
        } else if ("".equals(mail)) {
            errors.put(MAIL_FIELD_NAME, "Mail must not to be empty");
        } else {
            EmailValidator emailValidator = EmailValidator.getInstance();
            if (!emailValidator.isValid(mail)) {
                errors.put(MAIL_FIELD_NAME, "Invalid email format");
            }
        }


        String password = dto.getPassword();

        if (password == null) {
            errors.put(PASSWORD_FIELD_NAME, "Password is missing");
        } else if ("".equals(password)) {
            errors.put(PASSWORD_FIELD_NAME, "Password must not to be empty");
        }

        if (!errors.isEmpty()) {
            throw new NotValidUserBodyException(errors);
        }
    }

    private void sendVerificationCode(String mail) {
        String text = buildVerificationUrl(mail);
        this.mailSenderService.send(mail, text, "Verification");
    }

    private String buildVerificationUrl(String mail) {

        StringBuilder text = new StringBuilder();

        text.append(verification.getUrl())
                .append("?code=")
                .append(this.verificationCodeService.generateCode())
                .append("&mail=")
                .append(mail);

        return text.toString();
    }
}
