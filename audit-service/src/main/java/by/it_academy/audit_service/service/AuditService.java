package by.it_academy.audit_service.service;

import by.it_academy.audit_service.dao.api.IAuditDao;
import by.it_academy.audit_service.dao.entity.Audit;
import by.it_academy.audit_service.service.api.IAuditService;
import by.it_academy.task_manager_common.dto.CustomPage;
import org.springframework.core.convert.ConversionService;

import java.util.UUID;

public class AuditService implements IAuditService {

    private IAuditDao auditDao;

    private ConversionService conversionService;

    public AuditService(IAuditDao auditDao, ConversionService conversionService) {
        this.auditDao = auditDao;
        this.conversionService = conversionService;
    }

    @Override
    public CustomPage<Audit> getPage(Integer page, Integer size) {
        return null;
    }

    @Override
    public Audit get(UUID uuid) {
        return null;
    }
}
