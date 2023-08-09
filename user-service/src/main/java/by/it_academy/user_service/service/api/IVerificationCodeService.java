package by.it_academy.user_service.service.api;

import by.it_academy.user_service.core.dto.VerificationCodeCreateDto;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.dao.entity.VerificationCode;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IVerificationCodeService {

    VerificationCode create(VerificationCodeCreateDto dto);

    VerificationCode update(VerificationCodeCreateDto dto, UUID uuid, LocalDateTime dtUpdate);

    void delete(UUID uuid, LocalDateTime dtUpdate);

    VerificationCode get(UUID uuid);

    VerificationCode getByUser(User user);

}
