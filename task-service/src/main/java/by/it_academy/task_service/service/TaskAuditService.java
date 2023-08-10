package by.it_academy.task_service.service;

import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.enums.EssenceType;
import by.it_academy.task_service.service.api.IAuditClient;
import by.it_academy.task_service.service.api.ITaskAuditService;
import by.it_academy.task_service.utils.JwtTokenHandler;

import java.util.UUID;

public class TaskAuditService implements ITaskAuditService {

    private final IAuditClient auditClient;

    private final JwtTokenHandler tokenHandler;

    public TaskAuditService(IAuditClient auditClient, JwtTokenHandler jwtTokenHandler) {
        this.auditClient = auditClient;
        this.tokenHandler = jwtTokenHandler;
    }

    @Override
    public void save(String header, AuditCreateDto dto) {
        this.auditClient.create(header, dto);
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
}
