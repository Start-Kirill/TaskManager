package by.it_academy.user_service.service;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.user_service.core.dto.VerificationCodeCreateDto;
import by.it_academy.user_service.dao.api.IVerificationCodeDao;
import by.it_academy.user_service.dao.entity.VerificationCode;
import by.it_academy.user_service.service.api.IVerificationCodeService;
import by.it_academy.user_service.service.exceptions.common.VerificationCodeNotExistsException;
import by.it_academy.user_service.service.exceptions.common.VersionsNotMatchException;
import by.it_academy.user_service.service.exceptions.commonInternal.GeneratedDataNotCorrectException;
import by.it_academy.user_service.service.exceptions.commonInternal.InternalServerErrorException;
import by.it_academy.user_service.service.exceptions.commonInternal.UnknownConstraintException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VerificationCodeService implements IVerificationCodeService {

    private static final String UNIQUE_UUID_CONSTRAINT_NAME = "verification_code_pkey";

    private final IVerificationCodeDao verificationCodeDao;

    private final ConversionService conversionService;

    public VerificationCodeService(IVerificationCodeDao verificationCodeDao, ConversionService conversionService) {
        this.verificationCodeDao = verificationCodeDao;
        this.conversionService = conversionService;
    }

    @Transactional
    @Override
    public VerificationCode create(VerificationCodeCreateDto dto) {
        try {
            VerificationCode verificationCode = this.conversionService.convert(dto, VerificationCode.class);
            return this.verificationCodeDao.save(verificationCode);
        } catch (DataIntegrityViolationException ex) {
            if (ex.contains(ConstraintViolationException.class)) {
                String constraintName = ((ConstraintViolationException) ex.getCause()).getConstraintName();
                if (UNIQUE_UUID_CONSTRAINT_NAME.equals(constraintName)) {
                    List<ErrorResponse> errors = new ArrayList<>();
                    errors.add(new ErrorResponse(ErrorType.ERROR, "Internal failure of server. Duplicate uuid was generated. Repeat request or contact administrator"));
                    throw new GeneratedDataNotCorrectException(errors);
                }
                List<ErrorResponse> errors = new ArrayList<>();
                errors.add(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator"));
                throw new UnknownConstraintException(errors);
            }
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator"));
            throw new InternalServerErrorException(errors);
        }
    }

    @Transactional
    @Override
    public VerificationCode update(VerificationCodeCreateDto dto, UUID uuid, LocalDateTime dtUpdate) {
        try {
            VerificationCode verificationCode = this.get(uuid);
            if (!verificationCode.getDtUpdate().equals(dtUpdate)) {
                throw new VersionsNotMatchException(List.of(new ErrorResponse(ErrorType.ERROR, "Code was changed. Try to send verification code again")));
            }
            verificationCode.setCode(dto.getCode());
            return this.verificationCodeDao.save(verificationCode);
        } catch (DataIntegrityViolationException ex) {
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator"));
            throw new UnknownConstraintException(errors);
        } catch (OptimisticLockingFailureException ex) {
            throw new VersionsNotMatchException(List.of(new ErrorResponse(ErrorType.ERROR, "Code was changed. Try to send verification code again")));
        }
    }

    @Transactional
    @Override
    public void delete(UUID uuid, LocalDateTime dtUpdate) {
        try {
            VerificationCode verificationCode = this.get(uuid);
            if (!verificationCode.getDtUpdate().equals(dtUpdate)) {
                throw new VersionsNotMatchException(List.of(new ErrorResponse(ErrorType.ERROR, "Code was changed. Try to send verification code again")));
            }
            this.verificationCodeDao.delete(verificationCode);
        } catch (DataIntegrityViolationException ex) {
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator"));
            throw new UnknownConstraintException(errors);
        } catch (OptimisticLockingFailureException ex) {
            throw new VersionsNotMatchException(List.of(new ErrorResponse(ErrorType.ERROR, "Code was changed. Try to send verification code again")));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public VerificationCode getByUser(User user) {
        if (this.verificationCodeDao.existsByUser(user)) {
            return this.verificationCodeDao.findByUser(user);
        }
        throw new VerificationCodeNotExistsException(List.of(new ErrorResponse(ErrorType.ERROR, "Code for such user not exists")));

    }

    @Transactional(readOnly = true)
    @Override
    public VerificationCode get(UUID uuid) {
        if (this.verificationCodeDao.existsById(uuid)) {
            return this.verificationCodeDao.findById(uuid).orElseThrow();
        }
        throw new VerificationCodeNotExistsException(List.of(new ErrorResponse(ErrorType.ERROR, "Such code not exists")));

    }


}
