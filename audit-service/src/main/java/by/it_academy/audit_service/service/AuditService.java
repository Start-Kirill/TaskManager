package by.it_academy.audit_service.service;

import by.it_academy.audit_service.dao.entity.Audit;
import by.it_academy.audit_service.service.api.IAuditService;
import by.it_academy.taskManagerDto.dto.CustomPage;

import java.util.UUID;

public class AuditService implements IAuditService {
    @Override
    public CustomPage<Audit> getPage(Integer page, Integer size) {
        return null;
    }

    @Override
    public Audit get(UUID uuid) {
        return null;
    }
}
