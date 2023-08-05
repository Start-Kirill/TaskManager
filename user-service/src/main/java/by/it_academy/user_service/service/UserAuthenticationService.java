package by.it_academy.user_service.service;

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
import by.it_academy.user_service.service.api.IUserAuthenticationService;
import by.it_academy.user_service.service.api.IUserService;
import by.it_academy.user_service.service.api.IVerificationCodeService;
import by.it_academy.user_service.service.exceptions.common.NotVerifyUserException;
import by.it_academy.user_service.service.exceptions.structured.NotCorrectPasswordException;
import by.it_academy.user_service.service.exceptions.structured.NotValidUserBodyException;
import by.it_academy.user_service.utils.JwtTokenHandler;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.core.convert.ConversionService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {

    private static final String PASSWORD_FIELD_NAME = "password";

    private static final String MAIL_FIELD_NAME = "mail";

    private static final String FIO_FIELD_NAME = "fio";

    private final IUserService userService;

    private final IVerificationCodeService verificationCodeService;

    private final ConversionService conversionService;

    private final JavaMailSender emailSender;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenHandler tokenHandler;

    private final AppProperty.Verification verification;

    public UserAuthenticationService(IUserService userService,
                                     ConversionService conversionService,
                                     JavaMailSender emailSender,
                                     AppProperty property,
                                     IVerificationCodeService verificationCodeService,
                                     PasswordEncoder passwordEncoder,
                                     JwtTokenHandler tokenHandler) {
        this.userService = userService;
        this.conversionService = conversionService;
        this.emailSender = emailSender;
        this.verification = property.getVerification();
        this.verificationCodeService = verificationCodeService;
        this.passwordEncoder = passwordEncoder;
        this.tokenHandler = tokenHandler;
    }

    @Transactional
    @Override
    public void signIn(UserRegistrationDto dto) {

        validate(dto);

        UserCreateDto userCreateDto = this.conversionService.convert(dto, UserCreateDto.class);
        String code = UUID.randomUUID().toString().replace("-", "");
        User user = this.userService.save(userCreateDto);
        VerificationCodeCreateDto verificationCodeCreateDto = new VerificationCodeCreateDto(code, user);
        this.verificationCodeService.create(verificationCodeCreateDto);
        sendVerificationCode(dto.getMail(), code);
    }

    @Override
    public void verify(String code, String mail) {

        validateMail(mail);
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
    }

    //    TODO
    @Override
    public String login(UserLoginDto dto) {
        validate(dto);
        User user = this.userService.findByMail(dto.getMail());
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            Map<String, String> errors = new HashMap<>();
            errors.put(PASSWORD_FIELD_NAME, "Not correct password");
            throw new NotCorrectPasswordException(errors);
        }
        String token = this.tokenHandler.generateAccessToken(user.getUuid(), user.getMail(), user.getFio(), user.getRole());
        return token;
    }


    @Override
    public User getMe(UUID uuid) {
        return this.userService.get(uuid);
    }

    @Transactional
    @Override
    public void sendCodeAgain(String mail) {
        validateMail(mail);
        User user = this.userService.findByMail(mail);
        String code = UUID.randomUUID().toString().replace("-", "");
        VerificationCode verificationCode = this.verificationCodeService.getByUser(user);
        VerificationCodeCreateDto codeCreateDto = new VerificationCodeCreateDto(code, user);
        this.verificationCodeService.update(codeCreateDto, verificationCode.getUuid(), verificationCode.getDtUpdate());

        sendVerificationCode(mail, code);
    }

    private void validateMail(String mail) {
        List<ErrorResponse> errors = new ArrayList<>();

        if (mail == null) {
            errors.add(new ErrorResponse(ErrorType.ERROR, "Mail is missing"));
        } else if ("".equals(mail)) {
            errors.add(new ErrorResponse(ErrorType.ERROR, "Mail not to be empty"));
        } else {
            EmailValidator emailValidator = EmailValidator.getInstance();
            if (!emailValidator.isValid(mail)) {
                errors.add(new ErrorResponse(ErrorType.ERROR, "Invalid email format"));
            }
        }

        if (!errors.isEmpty()) {
            throw new NotVerifyUserException(errors);
        }
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
            errors.put(MAIL_FIELD_NAME, "Mail not to be empty");
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

    private void sendVerificationCode(String mail, String code) {

        String text = verification.getUrl() + "?code=" + code + "&mail=" + mail;

        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(mail);
            mimeMessageHelper.setSubject("Verification");
            mimeMessageHelper.setText(text);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        emailSender.send(mimeMessage);
    }
}
