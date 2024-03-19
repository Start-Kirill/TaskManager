package by.it_academy.report_service.service;

import by.it_academy.report_service.service.api.IAuditClientService;
import by.it_academy.report_service.utils.JwtTokenHandler;
import by.it_academy.task_manager_common.dto.*;
import by.it_academy.task_manager_common.enums.EssenceType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuditKafkaClientService implements IAuditClientService {

    private static final String TOPIC_AUDIT = "audit";

    private final JwtTokenHandler tokenHandler;

    private final KafkaTemplate<String, AuditCreateDto> kafkaAuditTemplate;

    public AuditKafkaClientService(JwtTokenHandler tokenHandler,
                                   KafkaTemplate<String, AuditCreateDto> kafkaAuditTemplate) {
        this.tokenHandler = tokenHandler;
        this.kafkaAuditTemplate = kafkaAuditTemplate;
    }

    @Override
    public void save(String header, AuditCreateDto dto) {
        this.kafkaAuditTemplate.send(TOPIC_AUDIT, header, dto);
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
        return null;
    }

    @Override
    public CustomPage<AuditDto> get(String token) {
        return null;
    }

    @Override
    public List<AuditDto> get(String token, ReportParamAudit paramAudit) {
        return null;
    }
}
