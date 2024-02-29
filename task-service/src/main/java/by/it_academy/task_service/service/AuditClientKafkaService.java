package by.it_academy.task_service.service;

import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.enums.EssenceType;
import by.it_academy.task_service.service.api.IAuditClientService;
import by.it_academy.task_service.utils.JwtTokenHandler;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Primary
public class AuditClientKafkaService implements IAuditClientService {

    private static final String TOPIC_AUDIT = "audit";

    private final KafkaTemplate<String, AuditCreateDto> kafkaAuditTemplate;

    private final JwtTokenHandler tokenHandler;

    public AuditClientKafkaService(KafkaTemplate<String, AuditCreateDto> kafkaAuditTemplate, JwtTokenHandler tokenHandler) {
        this.kafkaAuditTemplate = kafkaAuditTemplate;
        this.tokenHandler = tokenHandler;
    }

    @Override
    public void save(String header, AuditCreateDto dto) {
        this.kafkaAuditTemplate.send(TOPIC_AUDIT, header, dto);
    }

    @Override
    public void save(UserDetailsImpl userDetails, UUID performedEssence, String message, EssenceType type) {
        String token = this.tokenHandler.generateAccessToken(userDetails);
        this.save(token, performedEssence.toString(), message, type);
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
