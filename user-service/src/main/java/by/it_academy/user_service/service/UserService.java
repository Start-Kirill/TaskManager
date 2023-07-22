package by.it_academy.user_service.service;

import by.it_academy.user_service.core.dto.PageOfUsers;
import by.it_academy.user_service.core.dto.ResultOrError;
import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.core.dto.UserDto;
import by.it_academy.user_service.core.enums.ErrorType;
import by.it_academy.user_service.core.enums.UserRole;
import by.it_academy.user_service.core.enums.UserStatus;
import by.it_academy.user_service.core.errors.ErrorResponse;
import by.it_academy.user_service.core.errors.SpecificError;
import by.it_academy.user_service.core.errors.StructuredErrorResponse;
import by.it_academy.user_service.core.utils.Utils;
import by.it_academy.user_service.dao.api.IUserDao;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.service.api.IUserService;
import by.it_academy.user_service.service.support.passay.CyrillicEnglishCharacterData;
import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.exception.ConstraintViolationException;
import org.passay.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private static final String FIO_FIELD_NAME = "fio";

    private static final String MAIL_FIELD_NAME = "mail";

    private static final String PASSWORD_FIELD_NAME = "password";

    private static final String ROLE_FIELD_NAME = "role";

    private static final String STATUS_FIELD_NAME = "status";

    private static final String UUID_PARAM_NAME = "uuid";

    private static final String DT_UPDATE_PARAM_NAME = "dt_update";

    private static final String UNIQUE_MAIL_CONSTRAINT_NAME = "users_mail_key";

    private static final String UNIQUE_UUID_CONSTRAINT_NAME = "users_pkey";

    private IUserDao userDao;

    private ConversionService conversionService;

    public UserService(IUserDao userDao, ConversionService conversionService) {
        this.userDao = userDao;
        this.conversionService = conversionService;
    }


    @Override
    public ResultOrError save(UserCreateDto dto) {
        ResultOrError resultOrError = validate(dto);

        if (resultOrError.hasError()) {
            resultOrError.setHttpStatus(HttpStatus.BAD_REQUEST);
            return resultOrError;
        }

        try {
            User user = this.conversionService.convert(dto, User.class);

            user.setUuid(UUID.randomUUID());

            LocalDateTime now = LocalDateTime.now();
            user.setDateTimeCreate(now);
            user.setDateTimeCreate(now);

            User save = this.userDao.save(user);
            resultOrError.setUser(conversionService.convert(save, UserDto.class));
            resultOrError.setHttpStatus(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            String constraintName = ((ConstraintViolationException) ex.getCause()).getConstraintName();

            List<SpecificError> specificErrors = new ArrayList<>();
            List<ErrorResponse> errorResponses = new ArrayList<>();

            if (UNIQUE_MAIL_CONSTRAINT_NAME.equals(constraintName)) {
                specificErrors.add(new SpecificError("User with such email already exists", MAIL_FIELD_NAME));
                resultOrError.setHttpStatus(HttpStatus.BAD_REQUEST);
            } else if (UNIQUE_UUID_CONSTRAINT_NAME.equals(constraintName)) {
                errorResponses.add(new ErrorResponse(ErrorType.ERROR, "Internal failure of server. Duplicate uuid was generated. Repeat request or contact administrator"));
                resultOrError.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                errorResponses.add(new ErrorResponse(ErrorType.ERROR, "Unknown constraint was triggered"));
                resultOrError.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (!specificErrors.isEmpty()) {

                if (resultOrError.getStructuredErrorResponse() == null) {

                    resultOrError.setStructuredErrorResponse(Utils.makeStructuredError(specificErrors));

                } else {
                    StructuredErrorResponse structuredErrorResponse = resultOrError.getStructuredErrorResponse();
                    if (structuredErrorResponse.getErrors() == null) {
                        structuredErrorResponse.setErrors(specificErrors);
                    } else {
                        List<SpecificError> errors = structuredErrorResponse.getErrors();
                        errors.addAll(specificErrors);
                    }
                }
            } else {
                resultOrError.setErrorResponses(errorResponses);
            }

        }

        return resultOrError;
    }

    @Override
    public ResultOrError update(UserCreateDto dto, UUID uuid, Long version) {

        ResultOrError resultOrError = get(uuid);

        if (resultOrError.hasError()) {
            return resultOrError;
        }

        UserDto user = resultOrError.getUser();

        resultOrError = validate(dto);

        if (resultOrError.hasError()) {
            resultOrError.setHttpStatus(HttpStatus.BAD_REQUEST);
            return resultOrError;
        }

        Long realVersion = this.conversionService.convert(user.getDateTimeUpdate(), Long.class);
        if (!realVersion.equals(version)) {
            StructuredErrorResponse structuredErrorResponse = Utils.makeStructuredError("User date updates (versions) don't match. Get up-to-user", DT_UPDATE_PARAM_NAME);
            resultOrError.setStructuredErrorResponse(structuredErrorResponse);
            resultOrError.setHttpStatus(HttpStatus.BAD_REQUEST);
            return resultOrError;
        }

        User convertedUser = conversionService.convert(dto, User.class);
        convertedUser.setUuid(uuid);
        convertedUser.setDateTimeCreate(user.getDateTimeCreate());
        convertedUser.setDateTimeUpdate(user.getDateTimeUpdate());

        try {
            User save = this.userDao.save(convertedUser);
            resultOrError.setUser(conversionService.convert(save, UserDto.class));
            resultOrError.setHttpStatus(HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            String constraintName = ((ConstraintViolationException) ex.getCause()).getConstraintName();

            List<SpecificError> specificErrors = new ArrayList<>();
            List<ErrorResponse> errorResponses = new ArrayList<>();

            if (UNIQUE_MAIL_CONSTRAINT_NAME.equals(constraintName)) {
                specificErrors.add(new SpecificError("User with such email already exists", MAIL_FIELD_NAME));
                resultOrError.setHttpStatus(HttpStatus.BAD_REQUEST);
            } else {
                errorResponses.add(new ErrorResponse(ErrorType.ERROR, "Unknown constraint was triggered"));
                resultOrError.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (!specificErrors.isEmpty()) {
                if (resultOrError.getStructuredErrorResponse() == null) {

                    resultOrError.setStructuredErrorResponse(Utils.makeStructuredError(specificErrors));

                } else {
                    StructuredErrorResponse structuredErrorResponse = resultOrError.getStructuredErrorResponse();
                    if (structuredErrorResponse.getErrors() == null) {
                        structuredErrorResponse.setErrors(specificErrors);
                    } else {
                        List<SpecificError> errors = structuredErrorResponse.getErrors();
                        errors.addAll(specificErrors);
                    }
                }
            } else {
                resultOrError.setErrorResponses(errorResponses);
            }

        } catch (OptimisticLockingFailureException ex) {
            StructuredErrorResponse structuredErrorResponse = Utils.makeStructuredError("User date updates (versions) don't match. Get up-to-user", DT_UPDATE_PARAM_NAME);
            resultOrError.setStructuredErrorResponse(structuredErrorResponse);
            resultOrError.setHttpStatus(HttpStatus.BAD_REQUEST);
        }

        return resultOrError;
    }

    @Override
    public ResultOrError get(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<User> userPage = this.userDao.findAll(pageRequest);

        PageOfUsers pageOfUsers = new PageOfUsers();
        pageOfUsers.setNumber(page);
        pageOfUsers.setSize(size);
        pageOfUsers.setTotalPages(userPage.getTotalPages());
        pageOfUsers.setTotalElements(userPage.getTotalElements());
        if (page == 0) {
            pageOfUsers.setFirst(true);
        } else {
            pageOfUsers.setFirst(false);
        }

        if (userPage.hasContent()) {
            pageOfUsers.setNumberOfElements(userPage.getContent().size());
        } else {
            pageOfUsers.setNumberOfElements(0);
        }

        if (userPage.hasNext()) {
            pageOfUsers.setLast(false);
        } else {
            pageOfUsers.setLast(true);
        }
        List<UserDto> userDtos = userPage.getContent().stream().map(u -> conversionService.convert(u, UserDto.class)).toList();
        pageOfUsers.setContent(userDtos);

        ResultOrError resultOrError = new ResultOrError();
        resultOrError.setUsers(pageOfUsers);
        return resultOrError;
    }

    @Override
    public ResultOrError get(UUID uuid) {
        ResultOrError resultOrError = new ResultOrError();

        if (!this.userDao.existsById(uuid)) {
            StructuredErrorResponse structuredErrorResponse = Utils.makeStructuredError("User with such id doesn't exist", UUID_PARAM_NAME);
            resultOrError.setStructuredErrorResponse(structuredErrorResponse);
            resultOrError.setHttpStatus(HttpStatus.BAD_REQUEST);
        } else {
            User user = this.userDao.findById(uuid).orElseThrow();
            resultOrError.setUser(conversionService.convert(user, UserDto.class));
            resultOrError.setHttpStatus(HttpStatus.OK);
        }

        return resultOrError;
    }


    private ResultOrError validate(UserCreateDto dto) {
        ResultOrError resultOrError = new ResultOrError();
        List<SpecificError> specificErrorList = new ArrayList<>();

        String fio = dto.getFio();
        if (fio == null || "".equals(fio)) {
            specificErrorList.add(new SpecificError(
                    "Fio is required",
                    FIO_FIELD_NAME
            ));
        }

        String mail = dto.getMail();
        specificErrorList.addAll(validateMail(mail));


        String password = dto.getPassword();
        specificErrorList.addAll(validatePassword(password, mail));

        UserRole role = dto.getRole();
        if (role == null) {
            specificErrorList.add(new SpecificError(
                    "Role is required",
                    ROLE_FIELD_NAME
            ));
        }

        UserStatus status = dto.getStatus();
        if (status == null) {
            specificErrorList.add(new SpecificError(
                    "Status is required",
                    STATUS_FIELD_NAME
            ));
        }

        if (!specificErrorList.isEmpty()) {
            resultOrError.setStructuredErrorResponse(
                    new StructuredErrorResponse(ErrorType.STRUCTURED_ERROR, specificErrorList)
            );
        }

        return resultOrError;
    }

    private List<SpecificError> validateMail(String mail) {
        List<SpecificError> specificErrors = new ArrayList<>();
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (mail == null || "".equals(mail)) {
            specificErrors.add(new SpecificError(
                    "Mail is required",
                    MAIL_FIELD_NAME
            ));
        } else if (!emailValidator.isValid(mail)) {
            specificErrors.add(new SpecificError(
                    "Invalid email format",
                    MAIL_FIELD_NAME
            ));
        }
        return specificErrors;
    }

    private List<SpecificError> validatePassword(String password, String user) {
        List<SpecificError> specificErrors = new ArrayList<>();

        if (password == null || "".equals(password)) {
            specificErrors.add(new SpecificError(
                    "Password is required",
                    PASSWORD_FIELD_NAME
            ));
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
                StringBuilder errors = new StringBuilder();
                boolean needComma = false;
                for (RuleResultDetail rsd : details) {
                    if (needComma) {
                        errors.append(", ");
                    } else {
                        needComma = true;
                    }
                    errors.append(String.join(",", rsd.getErrorCodes()));
                }
                String message = "Password does not match requirements: " + errors;
                specificErrors.add(new SpecificError(message, PASSWORD_FIELD_NAME));
            }
        }
        return specificErrors;
    }


}
