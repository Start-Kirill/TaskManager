package by.it_academy.user_service.service;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.exceptions.common.VersionsNotMatchException;
import by.it_academy.task_manager_common.exceptions.commonInternal.GeneratedDataNotCorrectException;
import by.it_academy.task_manager_common.exceptions.commonInternal.InternalServerErrorException;
import by.it_academy.task_manager_common.exceptions.commonInternal.UnknownConstraintException;
import by.it_academy.user_service.config.property.AppProperty;
import by.it_academy.user_service.core.dto.VerificationCreateDto;
import by.it_academy.user_service.core.dto.VerificationUpdateDto;
import by.it_academy.user_service.core.enums.VerificationStatus;
import by.it_academy.user_service.dao.api.IVerificationDao;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.dao.entity.Verification;
import by.it_academy.user_service.service.api.IVerificationService;
import by.it_academy.user_service.service.exceptions.common.VerificationCodeNotExistsException;
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
public class VerificationService implements IVerificationService {

    private static final String UNIQUE_UUID_CONSTRAINT_NAME = "verification_code_pkey";

    private final IVerificationDao verificationDao;

    private final ConversionService conversionService;

    private final AppProperty.Verification verificationProp;

    public VerificationService(IVerificationDao verificationDao, ConversionService conversionService, AppProperty property) {
        this.verificationDao = verificationDao;
        this.conversionService = conversionService;
        this.verificationProp = property.getVerification();
    }

    @Transactional
    @Override
    public Verification save(VerificationCreateDto dto) {
        try {
            Verification verification = this.conversionService.convert(dto, Verification.class);
            verification.setUuid(UUID.randomUUID());
            verification.setCode(generateCode());
            verification.setUrl(verificationProp.getUrl());
            LocalDateTime now = LocalDateTime.now();
            verification.setDtCreate(now);
            verification.setDtUpdate(now);

            return this.verificationDao.save(verification);
        } catch (DataIntegrityViolationException ex) {
            if (ex.contains(ConstraintViolationException.class)) {
                String constraintName = ((ConstraintViolationException) ex.getCause()).getConstraintName();
                if (UNIQUE_UUID_CONSTRAINT_NAME.equals(constraintName)) {
                    throw new GeneratedDataNotCorrectException(List.of(new ErrorResponse(ErrorType.ERROR, "Internal failure of server. Duplicate uuid was generated. Repeat request or contact administrator")));
                }
            }
            throw new InternalServerErrorException(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
        }
    }

    @Transactional
    @Override
    public Verification update(VerificationUpdateDto dto, UUID uuid, LocalDateTime dtUpdate) {
        try {
            Verification verification = this.get(uuid);
            if (!verification.getDtUpdate().equals(dtUpdate)) {
                throw new VersionsNotMatchException(List.of(new ErrorResponse(ErrorType.ERROR, "Verification data were changed. Try to send a verification mail again")));
            }
            verification.setCode(dto.getCode());
            verification.setStatus(dto.getStatus());
            verification.setAttempt(dto.getAttempt());
            verification.setUrl(dto.getUrl());
            verification.setUser(dto.getUser());
            return this.verificationDao.save(verification);
        } catch (DataIntegrityViolationException ex) {
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator"));
            throw new UnknownConstraintException(errors);
        } catch (OptimisticLockingFailureException ex) {
            throw new VersionsNotMatchException(List.of(new ErrorResponse(ErrorType.ERROR, "Verification data were changed. Try to send a verification mail again")));
        }
    }


    @Transactional(readOnly = true)
    @Override
    public Verification get(User user) {
        if (this.verificationDao.existsByUser(user)) {
            return this.verificationDao.findByUser(user);
        }
        throw new VerificationCodeNotExistsException(List.of(new ErrorResponse(ErrorType.ERROR, "Verification data for such user not exist")));

    }

    @Transactional(readOnly = true)
    @Override
    public List<Verification> get(VerificationStatus status) {
        List<Verification> allByStatus = this.verificationDao.findAllByStatus(status);
        return allByStatus;
    }

    @Transactional(readOnly = true)
    @Override
    public Verification get(UUID uuid) {
        if (this.verificationDao.existsById(uuid)) {
            return this.verificationDao.findById(uuid).orElseThrow();
        }
        throw new VerificationCodeNotExistsException(List.of(new ErrorResponse(ErrorType.ERROR, "Such verification data not exist")));
    }


    @Override
    public String generateCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }


}
