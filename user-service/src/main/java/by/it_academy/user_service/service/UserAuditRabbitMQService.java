package by.it_academy.user_service.service;

import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.enums.EssenceType;
import by.it_academy.user_service.service.api.IUserAuditService;
import by.it_academy.user_service.utils.JwtTokenHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Primary
public class UserAuditRabbitMQService implements IUserAuditService {

    @Value("${spring.rabbitmq.userAuditQueue}")
    private String AUDIT_USER_QUEUE_NAME;

    private final RabbitTemplate rabbitTemplate;

    private final JwtTokenHandler tokenHandler;

    public UserAuditRabbitMQService(RabbitTemplate rabbitTemplate, JwtTokenHandler tokenHandler) {
        this.rabbitTemplate = rabbitTemplate;
        this.tokenHandler = tokenHandler;
    }

    @Override
    public void save(String header, AuditCreateDto dto) {
        rabbitTemplate.convertAndSend(AUDIT_USER_QUEUE_NAME, dto);
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
