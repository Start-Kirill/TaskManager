package by.it_academy.report_service.service;


import by.it_academy.report_service.service.api.IUserClient;
import by.it_academy.report_service.service.api.IUserClientService;
import by.it_academy.report_service.utils.JwtTokenHandler;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.dto.UserDto;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.exceptions.commonInternal.FeignErrorException;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserClientService implements IUserClientService {


    private final IUserClient userClient;

    private final JwtTokenHandler tokenHandler;

    public UserClientService(IUserClient userClient, JwtTokenHandler tokenHandler) {
        this.userClient = userClient;
        this.tokenHandler = tokenHandler;
    }

    @Override
    public UserDto get(String tokenHeader, UUID uuid) {
        try {
            return this.userClient.get(tokenHeader, uuid);
        } catch (FeignException ex) {
            throw new FeignErrorException(ex, List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
        }
    }

    @Override
    public UserDto get(UserDetailsImpl userDetails, UUID uuid) {
        String token = this.tokenHandler.generateAccessToken(userDetails);
        return get("Bearer " + token, uuid);
    }

    @Override
    public List<UserDto> get(UserDetailsImpl userDetails, Set<UUID> uuid) {
        String token = this.tokenHandler.generateAccessToken(userDetails);
        return get("Bearer " + token, uuid);
    }

    @Override
    public List<UserDto> get(String token, Set<UUID> users) {
        try {
            return this.userClient.findAllByUuid(token, users);
        } catch (FeignException ex) {
            throw new FeignErrorException(ex, List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
        }
    }


}
