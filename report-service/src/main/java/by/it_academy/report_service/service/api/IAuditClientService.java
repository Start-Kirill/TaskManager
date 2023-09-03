package by.it_academy.report_service.service.api;

import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.enums.EssenceType;

import java.util.UUID;

public interface IAuditClientService {

    void save(String header, AuditCreateDto dto);

    void save(UserDetailsImpl userDetails, UUID performedEssence, String message, EssenceType type);

    void save(String token, String performedEssence, String message, EssenceType type);

}
