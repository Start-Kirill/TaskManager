package by.it_academy.audit_service.endpoints.web.support.spring.converters;

import by.it_academy.audit_service.core.dto.AuditDto;
import by.it_academy.audit_service.dao.entity.Audit;
import by.it_academy.task_manager_common.dto.UserDto;
import org.springframework.core.convert.converter.Converter;

public class AuditToAuditDtoConverter implements Converter<Audit, AuditDto> {

    @Override
    public AuditDto convert(Audit source) {
        AuditDto auditDto = new AuditDto();

        auditDto.setUuid(source.getUuid());
        auditDto.setDtCreate(source.getDtCreate());

        UserDto userDto = new UserDto();
        userDto.setUuid(source.getUserUuid());
        userDto.setMail(source.getUserMail());
        userDto.setFio(source.getUserFio());
        userDto.setRole(source.getUserRole());

        auditDto.setUser(userDto);
        auditDto.setText(source.getText());
        auditDto.setType(source.getType());
        auditDto.setId(source.getId());

        return auditDto;
    }
}
