package by.it_academy.report_service.service.api;

import by.it_academy.report_service.core.dto.ReportParamAudit;
import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.AuditDto;
import by.it_academy.task_manager_common.dto.CustomPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(value = "ReportAudit", url = "${feign.audit.url}")
public interface IAuditClient {

    @PostMapping
    void create(@RequestHeader("Authorization") String authorizationHeader, @RequestBody AuditCreateDto dto);


    @GetMapping
    CustomPage<AuditDto> get(@RequestHeader("Authorization") String authorizationHeader);

    @GetMapping
    List<AuditDto> get(@RequestHeader("Authorization") String authorizationHeader, ReportParamAudit reportParamAudit);

}
