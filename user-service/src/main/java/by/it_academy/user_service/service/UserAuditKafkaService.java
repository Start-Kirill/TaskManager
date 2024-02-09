package by.it_academy.user_service.service;

import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.enums.EssenceType;
import by.it_academy.user_service.service.api.IUserAuditService;
import by.it_academy.user_service.utils.JwtTokenHandler;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Primary
public class UserAuditKafkaService implements IUserAuditService {

    private static final String TOPIC_AUDIT = "audit";

    private final KafkaTemplate<String, AuditCreateDto> kafkaAuditTemplate;

    private final JwtTokenHandler tokenHandler;

    public UserAuditKafkaService(KafkaTemplate<String, AuditCreateDto> kafkaAuditTemplate,
                                 JwtTokenHandler tokenHandler) {
        this.kafkaAuditTemplate = kafkaAuditTemplate;
        this.tokenHandler = tokenHandler;
    }

    @Override
    public void save(String header, AuditCreateDto dto) {
        this.kafkaAuditTemplate.send(TOPIC_AUDIT, header, dto);
    }
    

    @Override
    public void save(UserDetailsImpl userDetails, UUID performedUser, String message) {
        String token = this.tokenHandler.generateAccessToken(userDetails);
        this.save(token, performedUser.toString(), message);
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

    @Override
    public void saveBySystem(UUID performedUser, String message) {
        String systemAccessToken = this.tokenHandler.generateSystemAccessToken();
        save(systemAccessToken, performedUser.toString(), message);
    }
}
