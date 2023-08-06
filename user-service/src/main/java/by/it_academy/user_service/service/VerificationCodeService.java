package by.it_academy.user_service.service;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.exceptions.CommonErrorException;
import by.it_academy.task_manager_common.exceptions.CommonInternalErrorException;
import by.it_academy.user_service.core.dto.VerificationCodeCreateDto;
import by.it_academy.user_service.dao.api.IVerificationCodeDao;
import by.it_academy.task_manager_common.entity.User;
import by.it_academy.user_service.dao.entity.VerificationCode;
import by.it_academy.user_service.service.api.IVerificationCodeService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VerificationCodeService implements IVerificationCodeService {

    private IVerificationCodeDao verificationCodeDao;

    private ConversionService conversionService;

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
        } catch (Exception ex) {
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator"));
            throw new CommonInternalErrorException(errors);
        }
    }

    @Transactional
    @Override
    public VerificationCode update(VerificationCodeCreateDto dto, UUID uuid, LocalDateTime dtUpdate) {
        try {
            VerificationCode verificationCode = this.get(uuid);
            if (!verificationCode.getDtUpdate().equals(dtUpdate)) {
                throw new RuntimeException();
            }
            verificationCode.setCode(dto.getCode());
            return this.verificationCodeDao.save(verificationCode);
        } catch (Exception ex) {
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator"));
            throw new CommonInternalErrorException(errors);
        }
    }

    @Transactional
    @Override
    public void delete(UUID uuid, LocalDateTime dtUpdate) {
        try {
            VerificationCode verificationCode = this.get(uuid);
            if (!verificationCode.getDtUpdate().equals(dtUpdate)) {
                throw new RuntimeException();
            }
            this.verificationCodeDao.delete(verificationCode);
        } catch (Exception ex) {
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator"));
            throw new CommonInternalErrorException(errors);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public VerificationCode getByUser(User user) {
        if (this.verificationCodeDao.existsByUser(user)) {
            return this.verificationCodeDao.findByUser(user);
        } else {
            throw new CommonErrorException(List.of(new ErrorResponse(ErrorType.ERROR, "Code for such user not exists")));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public VerificationCode get(UUID uuid) {
        if (this.verificationCodeDao.existsById(uuid)) {
            return this.verificationCodeDao.findById(uuid).orElseThrow();
        } else {
            throw new CommonErrorException(List.of(new ErrorResponse(ErrorType.ERROR, "Such code not exists")));
        }
    }


}
