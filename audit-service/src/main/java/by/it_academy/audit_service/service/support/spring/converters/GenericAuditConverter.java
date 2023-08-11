package by.it_academy.audit_service.service.support.spring.converters;

import by.it_academy.audit_service.dao.entity.Audit;
import by.it_academy.audit_service.utils.JwtTokenHandler;
import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.enums.UserRole;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GenericAuditConverter implements GenericConverter {

    private final JwtTokenHandler tokenHandler;

    public GenericAuditConverter(JwtTokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> pairs = new HashSet<>();

        pairs.add(new ConvertiblePair(AuditCreateDto.class, Audit.class));

        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() == Audit.class) {
            return source;
        }

        Audit audit = new Audit();
        AuditCreateDto auditCreateDto = (AuditCreateDto) source;

        audit.setDtCreate(LocalDateTime.now());
        audit.setUuid(UUID.randomUUID());

        String userToken = auditCreateDto.getUserToken();

        if(UserRole.SYSTEM.toString().equals(tokenHandler.getRole(userToken))){
            audit.setUserRole(UserRole.SYSTEM);
        }else{
            audit.setUserUuid(UUID.fromString(tokenHandler.getUuid(userToken)));
            audit.setUserMail(tokenHandler.getMail(userToken));
            audit.setUserFio(tokenHandler.getFio(userToken));

            UserRole role;
            String handlerRole = tokenHandler.getRole(userToken);
            if (UserRole.ADMIN.toString().equals(handlerRole)) {
                role = UserRole.ADMIN;
            } else if (UserRole.USER.toString().equals(handlerRole)) {
                role = UserRole.USER;
            } else {
                role = UserRole.MANAGER;
            }
            audit.setUserRole(role);
        }

        audit.setText(auditCreateDto.getText());
        audit.setType(auditCreateDto.getType());
        audit.setId(auditCreateDto.getId());

        return audit;
    }
}
