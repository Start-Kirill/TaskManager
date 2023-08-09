package by.it_academy.user_service.service.api;

import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;

import java.util.UUID;

public interface IUserAuditService {

    void save(String header, AuditCreateDto dto);

    void save(UserDetailsImpl userDetails, UUID performedUser, String message);

    void save(String token, String performedUser, String message);

    void saveBySystem(UUID performedUser, String message);

}
