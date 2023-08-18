package by.it_academy.user_service.service.api;

import by.it_academy.user_service.core.dto.VerificationCreateDto;
import by.it_academy.user_service.core.dto.VerificationUpdateDto;
import by.it_academy.user_service.core.enums.VerificationStatus;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.dao.entity.Verification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IVerificationService {

    Verification save(VerificationCreateDto dto);

    Verification update(VerificationUpdateDto dto, UUID uuid, LocalDateTime dtUpdate);

    Verification get(UUID uuid);

    Verification get(User user);

    List<Verification> get(VerificationStatus status);

    String generateCode();

}
