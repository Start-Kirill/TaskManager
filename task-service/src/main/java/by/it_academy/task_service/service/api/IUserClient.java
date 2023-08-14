package by.it_academy.task_service.service.api;

import by.it_academy.task_manager_common.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@FeignClient(value = "userService", url = "${feign.user.url}")
public interface IUserClient {

    @GetMapping("/{uuid}")
    UserDto get(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("uuid") UUID uuid);

    @PatchMapping
    List<UserDto> findAllByUuid(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Set<UUID> users);
}
