package by.it_academy.audit_service.service;

import by.it_academy.audit_service.service.api.IUserClient;
import by.it_academy.audit_service.service.api.IUserClientService;
import by.it_academy.task_manager_common.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserClientService implements IUserClientService {

    private final IUserClient userClient;

    public UserClientService(IUserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public UserDto get(String token, UUID uuid) {
        return this.userClient.get(token, uuid);
    }
}
