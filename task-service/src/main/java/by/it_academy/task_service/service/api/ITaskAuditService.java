package by.it_academy.task_service.service.api;

import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.enums.EssenceType;

import java.util.UUID;

public interface ITaskAuditService {

    void save(String header, AuditCreateDto dto);

    void save(UserDetailsImpl userDetails, UUID performedTask, String message, EssenceType type);

    void save(String token, String performedTask, String message, EssenceType type);

}
