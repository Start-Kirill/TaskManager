package by.it_academy.user_service.service.api;

import by.it_academy.task_manager_common.dto.AuditCreateDto;

public interface IUserAuditService {

    void create(AuditCreateDto dto);

}
