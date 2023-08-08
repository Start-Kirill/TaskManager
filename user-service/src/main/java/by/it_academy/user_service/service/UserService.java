package by.it_academy.user_service.service;

import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.entity.User;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.enums.UserRole;
import by.it_academy.task_manager_common.enums.UserStatus;
import by.it_academy.task_manager_common.exceptions.structured.NotCorrectPageDataException;
import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.dao.api.IUserDao;
import by.it_academy.user_service.service.api.IUserAuditService;
import by.it_academy.user_service.service.api.IUserService;
import by.it_academy.user_service.service.exceptions.common.UserNotExistsException;
import by.it_academy.user_service.service.exceptions.common.VersionsNotMatchException;
import by.it_academy.user_service.service.exceptions.commonInternal.GeneratedDataNotCorrectException;
import by.it_academy.user_service.service.exceptions.commonInternal.InternalServerErrorException;
import by.it_academy.user_service.service.exceptions.commonInternal.UnknownConstraintException;
import by.it_academy.user_service.service.exceptions.structured.MailNotExistsException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class UserService implements IUserService {

    private static final String FIO_FIELD_NAME = "fio";

    private static final String MAIL_FIELD_NAME = "mail";

    private static final String PASSWORD_FIELD_NAME = "password";

    private static final String ROLE_FIELD_NAME = "role";

    private static final String STATUS_FIELD_NAME = "status";

    private static final String PAGE_FIELD_NAME = "page";

    private static final String SIZE_FIELD_NAME = "size";

    private static final String UNIQUE_MAIL_CONSTRAINT_NAME = "users_mail_key";

    private static final String UNIQUE_UUID_CONSTRAINT_NAME = "users_pkey";

    private final IUserDao userDao;

    private final ConversionService conversionService;

    private final IUserAuditService auditService;

    private final PasswordEncoder passwordEncoder;

    private final UserHolder userHolder;


    public UserService(IUserDao userDao,
                       ConversionService conversionService,
                       IUserAuditService auditService,
                       PasswordEncoder passwordEncoder,
                       UserHolder userHolder) {
        this.userDao = userDao;
        this.conversionService = conversionService;
        this.auditService = auditService;
        this.passwordEncoder = passwordEncoder;
        this.userHolder = userHolder;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public User save(UserCreateDto dto) {
        validate(dto);

        try {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
            User user = this.conversionService.convert(dto, User.class);
            user.setUuid(UUID.randomUUID());
            LocalDateTime now = LocalDateTime.now();
            user.setDateTimeCreate(now);
            user.setDateTimeUpdate(now);
            User save = this.userDao.saveAndFlush(user);

            return save;

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

    @Transactional
    @Override
    public User auditedSave(UserCreateDto dto) {
        User save = this.save(dto);
        this.auditService.create(userHolder.getUser(), save.getUuid(), "User was created");
        return save;
    }


    @Transactional
    @Override
    public User update(UserCreateDto dto, UUID uuid, LocalDateTime version) {

        validate(dto);

        User user = get(uuid);

        LocalDateTime realVersion = user.getDateTimeUpdate().truncatedTo(ChronoUnit.MILLIS);

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
            User save = this.userDao.saveAndFlush(convertedUser);
            this.auditService.create(this.userHolder.getUser(), save.getUuid(), "User was updated");
            return save;
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

    @Transactional(readOnly = true)
    @Override
    public CustomPage<User> get(Integer page, Integer size) {

        validate(page, size);

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<User> userPage = this.userDao.findAll(pageRequest);

        CustomPage<User> userPageOfUser = (CustomPage<User>) this.conversionService.convert(userPage, CustomPage.class);

        userPageOfUser.setNumber(page);
        userPageOfUser.setSize(size);

        return userPageOfUser;
    }

    @Transactional(readOnly = true)
    @Override
    public User get(UUID uuid) {

        if (!this.userDao.existsById(uuid)) {
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(new ErrorResponse(ErrorType.ERROR, "Such user doesn't exist"));
            throw new UserNotExistsException(errors);
        }

        return this.userDao.findById(uuid).orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public User findByMail(String mail) {
        if (this.userDao.existsByMail(mail)) {
            return this.userDao.findByMail(mail).orElseThrow();
        }
        Map<String, String> errors = new HashMap<>();
        errors.put(MAIL_FIELD_NAME, "User with such email not exists");
        throw new MailNotExistsException(errors);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByMail(String mail) {
        return this.userDao.existsByMail(mail);
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

            PasswordData passwordData;

            if (user != null) {
                passwordData = new PasswordData(user, password);
            } else {
                passwordData = new PasswordData(password);
            }

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

    private void validate(Integer page, Integer size) {
        Map<String, String> errors = new HashMap<>();

        if (page == null) {
            errors.put(PAGE_FIELD_NAME, "Page is missing");
        } else if (page < 0) {
            errors.put(PAGE_FIELD_NAME, "Page must not to be negative value");
        }

        if (size == null) {
            errors.put(SIZE_FIELD_NAME, "Size is missing");
        } else if (size < 1) {
            errors.put(SIZE_FIELD_NAME, "Size must not to be less than 1");
        }

        if (!errors.isEmpty()) {
            throw new NotCorrectPageDataException(errors);
        }

    }

}
