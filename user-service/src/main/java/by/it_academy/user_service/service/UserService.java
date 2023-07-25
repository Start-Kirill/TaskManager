package by.it_academy.user_service.service;

import by.it_academy.user_service.core.dto.CustomPage;
import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.core.enums.ErrorType;
import by.it_academy.user_service.core.enums.UserRole;
import by.it_academy.user_service.core.enums.UserStatus;
import by.it_academy.user_service.core.errors.ErrorResponse;
import by.it_academy.user_service.dao.api.IUserDao;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.service.api.IUserService;
import by.it_academy.user_service.service.exceptions.common.*;
import by.it_academy.user_service.service.exceptions.structured.NotValidUserBodyException;
import by.it_academy.user_service.service.support.passay.CyrillicEnglishCharacterData;
import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.exception.ConstraintViolationException;
import org.passay.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService implements IUserService {

    private static final String FIO_FIELD_NAME = "fio";

    private static final String MAIL_FIELD_NAME = "mail";

    private static final String PASSWORD_FIELD_NAME = "password";

    private static final String ROLE_FIELD_NAME = "role";

    private static final String STATUS_FIELD_NAME = "status";

    private static final String UNIQUE_MAIL_CONSTRAINT_NAME = "users_mail_key";

    private static final String UNIQUE_UUID_CONSTRAINT_NAME = "users_pkey";

    private IUserDao userDao;

    private ConversionService conversionService;

    public UserService(IUserDao userDao, ConversionService conversionService) {
        this.userDao = userDao;
        this.conversionService = conversionService;
    }


    @Override
    public void save(UserCreateDto dto) {
        validate(dto);

        try {
            User user = this.conversionService.convert(dto, User.class);

            user.setUuid(UUID.randomUUID());

            LocalDateTime now = LocalDateTime.now();
            user.setDateTimeCreate(now);
            user.setDateTimeCreate(now);

            User save = this.userDao.save(user);
        } catch (DataIntegrityViolationException ex) {
            if (ex.contains(ConstraintViolationException.class)) {
                String constraintName = ((ConstraintViolationException) ex.getCause()).getConstraintName();

                if (UNIQUE_MAIL_CONSTRAINT_NAME.equals(constraintName)) {
                    Map<String, String> errors = new HashMap<>();
                    errors.put(MAIL_FIELD_NAME, "User with such email already exists");
                    throw new NotValidUserBodyException(errors);
                } else if (UNIQUE_UUID_CONSTRAINT_NAME.equals(constraintName)) {
                    List<ErrorResponse> errors = new ArrayList<>();
                    errors.add(new ErrorResponse(ErrorType.ERROR, "Internal failure of server. Duplicate uuid was generated. Repeat request or contact administrator"));
                    throw new GeneratedDataNotCorrectException(errors);
                } else {
                    List<ErrorResponse> errors = new ArrayList<>();
                    errors.add(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator"));
                    throw new UnknownConstraintException(errors);
                }
            } else {
                List<ErrorResponse> errors = new ArrayList<>();
                errors.add(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator"));
                throw new InternalServerErrorException(errors);
            }
        }

    }

    @Override
    public void update(UserCreateDto dto, UUID uuid, Long version) {

        validate(dto);

        User user = get(uuid);


        Long realVersion = this.conversionService.convert(user.getDateTimeUpdate(), Long.class);

        if (!realVersion.equals(version)) {
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(new ErrorResponse(ErrorType.ERROR, "User date updates (versions) don't match. Get up-to-date user"));
            throw new VersionsNotMatchException(errors);
        }

        User convertedUser = conversionService.convert(dto, User.class);
        convertedUser.setUuid(uuid);
        convertedUser.setDateTimeCreate(user.getDateTimeCreate());
        convertedUser.setDateTimeUpdate(user.getDateTimeUpdate());

        try {
            this.userDao.save(convertedUser);
        } catch (DataIntegrityViolationException ex) {
            if (ex.contains(ConstraintViolationException.class)) {
                String constraintName = ((ConstraintViolationException) ex.getCause()).getConstraintName();

                if (UNIQUE_MAIL_CONSTRAINT_NAME.equals(constraintName)) {
                    Map<String, String> errors = new HashMap<>();
                    errors.put(MAIL_FIELD_NAME, "User with such email already exists");
                    throw new NotValidUserBodyException(errors);
                } else {
                    List<ErrorResponse> errors = new ArrayList<>();
                    errors.add(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator"));
                    throw new UnknownConstraintException(errors);
                }
            } else {
                List<ErrorResponse> errors = new ArrayList<>();
                errors.add(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator"));
                throw new InternalServerErrorException(errors);
            }

        } catch (OptimisticLockingFailureException ex) {
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(new ErrorResponse(ErrorType.ERROR, "User date updates (versions) don't match. Get up-to-date user"));
            throw new VersionsNotMatchException(errors);
        }

    }

    @Override
    public CustomPage<User> get(Integer page, Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<User> userPage = this.userDao.findAll(pageRequest);

        CustomPage<User> userPageOfUser = new CustomPage<>();
        userPageOfUser.setNumber(page);
        userPageOfUser.setSize(size);
        userPageOfUser.setTotalPages(userPage.getTotalPages());
        userPageOfUser.setTotalElements(userPage.getTotalElements());
        if (page == 0) {
            userPageOfUser.setFirst(true);
        } else {
            userPageOfUser.setFirst(false);
        }

        if (userPage.hasContent()) {
            userPageOfUser.setNumberOfElements(userPage.getContent().size());
        } else {
            userPageOfUser.setNumberOfElements(0);
        }

        if (userPage.hasNext()) {
            userPageOfUser.setLast(false);
        } else {
            userPageOfUser.setLast(true);
        }

        userPageOfUser.setContent(userPage.getContent());

        return userPageOfUser;
    }

    @Override
    public User get(UUID uuid) {

        if (!this.userDao.existsById(uuid)) {
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(new ErrorResponse(ErrorType.ERROR, "Such user doesn't exist"));
            throw new UserNotExistsException(errors);
        }

        return this.userDao.findById(uuid).orElseThrow();
    }


    private void validate(UserCreateDto dto) {
        Map<String, String> errors = new HashMap<>();

        String fio = dto.getFio();
        if (fio == null || "".equals(fio)) {
            errors.put(FIO_FIELD_NAME, "Fio is required");
        }

        String mail = dto.getMail();
        errors.putAll(validateMail(mail));

        String password = dto.getPassword();
        errors.putAll(validatePassword(password, mail));

        UserRole role = dto.getRole();
        if (role == null) {
            errors.put(ROLE_FIELD_NAME, "Role is required");
        }

        UserStatus status = dto.getStatus();
        if (status == null) {
            errors.put(STATUS_FIELD_NAME, "Status is required");
        }

        if (!errors.isEmpty()) {
            throw new NotValidUserBodyException(errors);
        }
    }

    private Map<String, String> validateMail(String mail) {
        EmailValidator emailValidator = EmailValidator.getInstance();

        Map<String, String> errors = new HashMap<>();

        if (mail == null || "".equals(mail)) {
            errors.put(MAIL_FIELD_NAME, "Mail is required");
        } else if (!emailValidator.isValid(mail)) {
            errors.put(MAIL_FIELD_NAME, "Invalid email format");
        }
        return errors;
    }

    private Map<String, String> validatePassword(String password, String user) {
        Map<String, String> errors = new HashMap<>();

        if (password == null || "".equals(password)) {
            errors.put(PASSWORD_FIELD_NAME, "Password is required");
        } else {
            PasswordData passwordData = new PasswordData(user, password);
            PasswordValidator passwordValidator = new PasswordValidator(
                    new LengthRule(8, 30),
                    new CharacterRule(EnglishCharacterData.Special),
                    new CharacterRule(CyrillicEnglishCharacterData.UpperCase),
                    new UsernameRule(false, true, MatchBehavior.Contains)
            );
            RuleResult validate = passwordValidator.validate(passwordData);
            if (!validate.isValid()) {
                List<RuleResultDetail> details = validate.getDetails();
                StringBuilder stringErrors = new StringBuilder();
                boolean needComma = false;
                for (RuleResultDetail rsd : details) {
                    if (needComma) {
                        stringErrors.append(", ");
                    } else {
                        needComma = true;
                    }
                    stringErrors.append(String.join(",", rsd.getErrorCodes()));
                }
                String message = "Password does not match requirements: " + stringErrors;
                errors.put(PASSWORD_FIELD_NAME, message);
            }
        }
        return errors;
    }


}
