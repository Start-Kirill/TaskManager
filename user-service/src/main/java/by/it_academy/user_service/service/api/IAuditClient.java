package by.it_academy.user_service.service.api;

import by.it_academy.task_manager_common.dto.AuditCreateDto;
import feign.Headers;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuditClient {

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    void create(@RequestBody AuditCreateDto dto);
}
