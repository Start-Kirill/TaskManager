package by.it_academy.user_service.service;

import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.core.dto.UserLoginDto;
import by.it_academy.user_service.core.dto.UserRegistrationDto;
import by.it_academy.user_service.core.enums.UserStatus;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.service.api.IUserAuthenticationService;
import by.it_academy.user_service.service.api.IUserService;
import by.it_academy.user_service.service.exceptions.structured.NotCorrectPasswordException;
import by.it_academy.user_service.service.exceptions.structured.NotValidUserBodyException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.core.convert.ConversionService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {

    private static final String PASSWORD_FIELD_NAME = "password";

    private static final String MAIL_FIELD_NAME = "mail";

    private IUserService userService;

    private ConversionService conversionService;

    private JavaMailSender emailSender;


    public UserAuthenticationService(IUserService userService, ConversionService conversionService, JavaMailSender emailSender) {
        this.userService = userService;
        this.conversionService = conversionService;
        this.emailSender = emailSender;
    }

    //    TODO
    @Override
    public void signIn(UserRegistrationDto dto) {

        UserCreateDto convert = this.conversionService.convert(dto, UserCreateDto.class);
        this.userService.save(convert);

        String code = "hello";

        MimeMessage mimeMessage = emailSender.createMimeMessage();

        String text = "http://localhost/users/verification?code=" + code + "&mail=" + dto.getMail();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(dto.getMail());
            mimeMessageHelper.setSubject("Verification");
            mimeMessageHelper.setText(text);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


        emailSender.send(mimeMessage);

    }

    //    TODO
    @Override
    public void verify(String code, String mail) {
        validateMail(mail);
        User user = this.userService.findByMail(mail);
        user.setStatus(UserStatus.ACTIVATED);

        UserCreateDto userCreateDto = this.conversionService.convert(user, UserCreateDto.class);

        this.userService.update(userCreateDto, user.getUuid(), user.getDateTimeUpdate().truncatedTo(ChronoUnit.MILLIS));
    }

    //    TODO
    @Override
    public void login(UserLoginDto dto) {
        User user = this.userService.findByMail(dto.getMail());
        if (!user.getPassword().equals(dto.getPassword())) {
            Map<String, String> errors = new HashMap<>();
            errors.put(PASSWORD_FIELD_NAME, "Not correct password");
            throw new NotCorrectPasswordException(errors);
        }
    }

    //    TODO
    @Override
    public User getMe() {
        return null;
    }

    private void validateMail(String mail) {
        EmailValidator emailValidator = EmailValidator.getInstance();

        if (!emailValidator.isValid(mail)) {
            Map<String, String> errors = new HashMap<>();
            errors.put(MAIL_FIELD_NAME, "Invalid email format");
            throw new NotValidUserBodyException(errors);
        }
    }
}
