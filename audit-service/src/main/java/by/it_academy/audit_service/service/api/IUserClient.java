package by.it_academy.audit_service.service.api;

import by.it_academy.task_manager_common.dto.UserDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(value = "userService", url = "${feign.user.url}")
public interface IUserClient {

    @GetMapping("/{uuid}")
    UserDto get(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("uuid") UUID uuid);
}
