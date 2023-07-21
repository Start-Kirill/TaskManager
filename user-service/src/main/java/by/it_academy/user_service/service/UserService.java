package by.it_academy.user_service.service;

import by.it_academy.user_service.core.dto.ResultOrError;
import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.core.enums.ErrorType;
import by.it_academy.user_service.core.enums.UserRole;
import by.it_academy.user_service.core.enums.UserStatus;
import by.it_academy.user_service.core.errors.ErrorResponse;
import by.it_academy.user_service.core.errors.SpecificError;
import by.it_academy.user_service.core.errors.StructuredErrorResponse;
import by.it_academy.user_service.dao.api.IUserDao;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.service.api.IUserService;
import by.it_academy.user_service.service.support.passay.CyrillicEnglishCharacterData;
import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.exception.ConstraintViolationException;
import org.passay.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    private static final String FIO_FIELD_NAME = "fio";

    private static final String MAIL_FIELD_NAME = "mail";

    private static final String PASSWORD_FIELD_NAME = "password";

    private static final String ROLE_FIELD_NAME = "role";

    private static final String STATUS_FIELD_NAME = "status";

    private static final String UNIQUE_MAIL_CONSTRAINT_NAME = "users_mail_key";

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
            return resultOrError;
        }

        try {
            User user = this.conversionService.convert(dto, User.class);
            User save = this.userDao.save(user);
            resultOrError.setUser(save);
        } catch (DataIntegrityViolationException ex) {
            String constraintName = ((ConstraintViolationException) ex.getCause()).getConstraintName();

            List<SpecificError> specificErrors = new ArrayList<>();
            List<ErrorResponse> errorResponses = new ArrayList<>();

            if (UNIQUE_MAIL_CONSTRAINT_NAME.equals(constraintName)) {
                specificErrors.add(new SpecificError("User with such email already exists", MAIL_FIELD_NAME));
            } else {
                errorResponses.add(new ErrorResponse(ErrorType.ERROR, "Unknown constraint was triggered"));
            }

            if (!specificErrors.isEmpty()) {
                if (resultOrError.getStructuredErrorResponse() == null) {
                    StructuredErrorResponse structuredErrorResponse = new StructuredErrorResponse();
                    structuredErrorResponse.setErrors(specificErrors);
                    structuredErrorResponse.setLogref(ErrorType.STRUCTURED_ERROR);
                    resultOrError.setStructuredErrorResponse(structuredErrorResponse);
                } else {
                    StructuredErrorResponse structuredErrorResponse = resultOrError.getStructuredErrorResponse();
                    if (structuredErrorResponse.getErrors() == null) {
                        structuredErrorResponse.setErrors(specificErrors);
                    } else {
                        List<SpecificError> errors = structuredErrorResponse.getErrors();
                        errors.addAll(specificErrors);
                    }
                }
            }

            if (!errorResponses.isEmpty()) {
                resultOrError.setErrorResponses(errorResponses);
            }

        }
        return resultOrError;
    }

    @Override
    public ResultOrError update(UserCreateDto dto) {
        return null;
    }

    @Override
    public ResultOrError get() {
        return null;
    }

    @Override
    public ResultOrError get(Long id) {
        return null;
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
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (mail == null || "".equals(mail)) {
            specificErrorList.add(new SpecificError(
                    "Mail is required",
                    MAIL_FIELD_NAME
            ));
        } else if (!emailValidator.isValid(mail)) {
            specificErrorList.add(new SpecificError(
                    "Invalid email format",
                    MAIL_FIELD_NAME
            ));
        }
        String password = dto.getPassword();
        if (password == null || "".equals(password)) {
            specificErrorList.add(new SpecificError(
                    "Password is required",
                    PASSWORD_FIELD_NAME
            ));
        } else {
            PasswordData passwordData = new PasswordData(mail, password);
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
                specificErrorList.add(new SpecificError(message, PASSWORD_FIELD_NAME));
            }
        }

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

}
