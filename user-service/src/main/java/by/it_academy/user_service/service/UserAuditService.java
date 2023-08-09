package by.it_academy.user_service.service;

import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.enums.EssenceType;
import by.it_academy.user_service.service.api.IAuditClient;
import by.it_academy.user_service.service.api.IUserAuditService;
import by.it_academy.user_service.utils.JwtTokenHandler;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserAuditService implements IUserAuditService {

    private final IAuditClient auditClient;

    private final JwtTokenHandler tokenHandler;

    public UserAuditService(IAuditClient auditClient, JwtTokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
        this.auditClient = auditClient;
    }

    @Override
    public void save(String header, AuditCreateDto dto) {
        this.auditClient.create(header, dto);
    }

    @Override
    public void save(UserDetailsImpl userDetails, UUID performedUser, String message) {
        String token = this.tokenHandler.generateAccessToken(userDetails);
        save(token, performedUser.toString(), message);
    }

    @Override
    public void saveBySystem(UUID performedUser, String message) {
        String systemAccessToken = this.tokenHandler.generateSystemAccessToken();
        save(systemAccessToken, performedUser.toString(), message);
    }

    @Override
    public void save(String token, String performedUser, String message) {

        AuditCreateDto auditCreateDto = new AuditCreateDto();

        auditCreateDto.setUserToken(token);
        auditCreateDto.setId(performedUser);
        auditCreateDto.setType(EssenceType.USER);
        auditCreateDto.setText(message);


        save("Bearer " + token, auditCreateDto);
    }


}
