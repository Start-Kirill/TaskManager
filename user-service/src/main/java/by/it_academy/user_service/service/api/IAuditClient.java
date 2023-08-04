package by.it_academy.user_service.service.api;

import by.it_academy.task_manager_common.dto.AuditCreateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "userAudit", url = "${feign.url}")
public interface IAuditClient {

    @PostMapping
    void create(@RequestBody AuditCreateDto dto);
}
