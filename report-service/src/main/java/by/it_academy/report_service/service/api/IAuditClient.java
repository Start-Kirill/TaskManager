package by.it_academy.report_service.service.api;

import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.AuditDto;
import by.it_academy.task_manager_common.dto.CustomPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@FeignClient(value = "ReportAudit", url = "${feign.audit.url}")
public interface IAuditClient {

    @PostMapping
    void create(@RequestHeader("Authorization") String authorizationHeader, @RequestBody AuditCreateDto dto);


    @GetMapping
    CustomPage<AuditDto> get(@RequestHeader("Authorization") String authorizationHeader);

    @GetMapping("/report")
    List<AuditDto> get(@RequestHeader("Authorization") String authorizationHeader,
                       @RequestParam(name = "user") UUID user,
                       @RequestParam(name = "from") LocalDateTime from,
                       @RequestParam(name = "to") LocalDateTime to);

}
