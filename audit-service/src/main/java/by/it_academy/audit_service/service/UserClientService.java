package by.it_academy.audit_service.service;

import by.it_academy.audit_service.service.api.IUserClient;
import by.it_academy.audit_service.service.api.IUserClientService;
import by.it_academy.task_manager_common.dto.UserDto;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.exceptions.commonInternal.FeignErrorException;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserClientService implements IUserClientService {

    private final IUserClient userClient;

    public UserClientService(IUserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public UserDto get(String token, UUID uuid) {
        try {
            return this.userClient.get(token, uuid);
        } catch (FeignException ex) {
            throw new FeignErrorException(ex, List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
        }
    }
}
