package by.it_academy.user_service.service.api;

import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;

import java.util.UUID;

public interface IUserAuditService {

    void create(String header ,AuditCreateDto dto);

    void create(UserDetailsImpl userDetails, UUID createdUser, String message);

}
