package by.it_academy.report_service.service;

import by.it_academy.report_service.service.api.IAuditClient;
import by.it_academy.report_service.service.api.IAuditClientService;
import by.it_academy.report_service.utils.JwtTokenHandler;
import by.it_academy.task_manager_common.dto.*;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.enums.EssenceType;
import by.it_academy.task_manager_common.exceptions.commonInternal.FeignErrorException;
import feign.FeignException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Primary
public class AuditFeignClientService implements IAuditClientService {

    private final IAuditClient auditClient;

    private final JwtTokenHandler tokenHandler;

    public AuditFeignClientService(IAuditClient auditClient, JwtTokenHandler jwtTokenHandler) {
        this.auditClient = auditClient;
        this.tokenHandler = jwtTokenHandler;
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
    public void save(UserDetailsImpl userDetails, UUID performedEssence, String message, EssenceType type) {
        String token = this.tokenHandler.generateAccessToken(userDetails);
        save(token, performedEssence.toString(), message, type);
    }

    @Override
    public void save(String token, String performedEssence, String message, EssenceType type) {
        AuditCreateDto auditCreateDto = new AuditCreateDto();

        auditCreateDto.setUserToken(token);
        auditCreateDto.setId(performedEssence);
        auditCreateDto.setType(type);
        auditCreateDto.setText(message);


        save("Bearer " + token, auditCreateDto);
    }

    @Override
    public CustomPage<AuditDto> get(UserDetailsImpl userDetails) {
        String token = this.tokenHandler.generateAccessToken(userDetails);
        return this.auditClient.get("Bearer " + token);
    }

    @Override
    public CustomPage<AuditDto> get(String token) {
        return this.auditClient.get("Bearer " + token);
    }

    @Override
    public List<AuditDto> get(String token, ReportParamAudit paramAudit) {
        return this.auditClient.get("Bearer " + token, paramAudit.getUser(), paramAudit.getFrom(), paramAudit.getTo());
    }

}
