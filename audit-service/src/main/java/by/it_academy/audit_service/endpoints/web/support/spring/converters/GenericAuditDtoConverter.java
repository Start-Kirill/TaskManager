package by.it_academy.audit_service.endpoints.web.support.spring.converters;

import by.it_academy.audit_service.core.dto.AuditDto;
import by.it_academy.audit_service.dao.entity.Audit;
import by.it_academy.task_manager_common.dto.UserDto;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.HashSet;
import java.util.Set;

public class GenericAuditDtoConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> convertiblePairs = new HashSet<>();
        convertiblePairs.add(new ConvertiblePair(Audit.class, AuditDto.class));
        return convertiblePairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() == AuditDto.class) {
            return source;
        }

        AuditDto auditDto = new AuditDto();

        Audit audit = (Audit) source;

        auditDto.setUuid(audit.getUuid());
        auditDto.setDtCreate(audit.getDtCreate());

        UserDto userDto = new UserDto();
        userDto.setUuid(audit.getUserUuid());
        userDto.setMail(audit.getUserMail());
        userDto.setFio(audit.getUserFio());
        userDto.setRole(audit.getUserRole());

        auditDto.setUser(userDto);
        auditDto.setText(audit.getText());
        auditDto.setType(audit.getType());
        auditDto.setId(audit.getId());

        return auditDto;

    }
}
