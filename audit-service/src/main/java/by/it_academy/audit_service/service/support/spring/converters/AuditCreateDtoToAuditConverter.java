package by.it_academy.audit_service.service.support.spring.converters;

import by.it_academy.audit_service.dao.entity.Audit;
import by.it_academy.audit_service.utils.JwtTokenHandler;
import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.enums.UserRole;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

public class AuditCreateDtoToAuditConverter implements Converter<AuditCreateDto, Audit> {

    private final JwtTokenHandler tokenHandler;

    public AuditCreateDtoToAuditConverter(JwtTokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Override
    public Audit convert(AuditCreateDto source) {
        Audit audit = new Audit();

        String userToken = source.getUserToken();

        if (UserRole.SYSTEM.toString().equals(tokenHandler.getRole(userToken))) {
            audit.setUserRole(UserRole.SYSTEM);
        } else {
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

        audit.setText(source.getText());
        audit.setType(source.getType());
        audit.setId(source.getId());

        return audit;
    }
}
