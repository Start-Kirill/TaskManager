package by.it_academy.report_service.service;

import by.it_academy.report_service.dao.entity.Report;
import by.it_academy.report_service.service.api.IReportBuilder;
import by.it_academy.task_manager_common.dto.AuditDto;
import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import org.springframework.stereotype.Service;

@Service
public class ExcelReportBuilder implements IReportBuilder {

    private final UserHolder userHolder;

    private final AuditClientService auditClientService;

    public ExcelReportBuilder(UserHolder userHolder, AuditClientService auditClientService) {
        this.userHolder = userHolder;
        this.auditClientService = auditClientService;
    }

    @Override
    public String build(Report report) {
        UserDetailsImpl user = userHolder.getUser();
        CustomPage<AuditDto> auditDtoCustomPage = this.auditClientService.get(user);
        return null;
    }
}
