package by.it_academy.task_service.service;


import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.dto.UserDto;
import by.it_academy.task_service.service.api.IUserClient;
import by.it_academy.task_service.service.api.IUserClientService;
import by.it_academy.task_service.utils.JwtTokenHandler;
import org.springframework.stereotype.Service;

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
        return this.userClient.get(tokenHeader, uuid);
    }

    @Override
    public UserDto get(UserDetailsImpl userDetails, UUID uuid) {
        String token = this.tokenHandler.generateAccessToken(userDetails);
        return get("Bearer " + token, uuid);
    }


}
