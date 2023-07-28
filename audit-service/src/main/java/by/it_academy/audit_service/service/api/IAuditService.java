package by.it_academy.audit_service.service.api;

import by.it_academy.audit_service.dao.entity.Audit;
import by.it_academy.task_manager_common.dto.CustomPage;

import java.util.UUID;

public interface IAuditService {

    CustomPage<Audit> getPage(Integer page, Integer size);

    Audit get(UUID uuid);
}
