package by.it_academy.user_service.service;

import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.enums.EssenceType;
import by.it_academy.task_manager_common.exceptions.commonInternal.FeignErrorException;
import by.it_academy.user_service.service.api.IAuditClient;
import by.it_academy.user_service.service.api.IUserAuditService;
import by.it_academy.user_service.utils.JwtTokenHandler;
import feign.FeignException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Primary
public class UserAuditFeignService implements IUserAuditService {

    private final IAuditClient auditClient;

    private final JwtTokenHandler tokenHandler;

    public UserAuditFeignService(IAuditClient auditClient, JwtTokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
        this.auditClient = auditClient;
    }

    @Override
    public void save(String header, AuditCreateDto dto) {
        try {
            this.auditClient.create(header, dto);
        } catch (FeignException ex) {
            throw new FeignErrorException(ex, List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
        }
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
