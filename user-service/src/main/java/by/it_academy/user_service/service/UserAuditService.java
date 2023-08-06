package by.it_academy.user_service.service;

import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.user_service.service.api.IAuditClient;
import by.it_academy.user_service.service.api.IUserAuditService;
import org.springframework.stereotype.Service;

@Service
public class UserAuditService implements IUserAuditService {

    private IAuditClient auditClient;

    public UserAuditService(IAuditClient auditClient) {
        this.auditClient = auditClient;
    }

    @Override
    public void create(String header, AuditCreateDto dto) {
        this.auditClient.create(header, dto);
    }

}
