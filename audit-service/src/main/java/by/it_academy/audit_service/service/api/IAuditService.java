package by.it_academy.audit_service.service.api;

import by.it_academy.audit_service.dao.entity.Audit;
import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_manager_common.dto.ReportParamAudit;

import java.util.List;
import java.util.UUID;

public interface IAuditService {

    CustomPage<Audit> getPage(Integer page, Integer size);

    Audit get(UUID uuid);

    void create(AuditCreateDto dto);

    List<Audit> getForReport(ReportParamAudit reportParamAudit);
}
