package by.it_academy.report_service.service.api;

import by.it_academy.task_manager_common.dto.ReportParamAudit;
import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.AuditDto;
import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.enums.EssenceType;

import java.util.List;
import java.util.UUID;

public interface IAuditClientService {

    void save(String header, AuditCreateDto dto);

    void save(UserDetailsImpl userDetails, UUID performedEssence, String message, EssenceType type);

    void save(String token, String performedEssence, String message, EssenceType type);

    CustomPage<AuditDto> get(UserDetailsImpl userDetails);

    CustomPage<AuditDto> get(String token);

    List<AuditDto> get(String token, ReportParamAudit paramAudit);

}
