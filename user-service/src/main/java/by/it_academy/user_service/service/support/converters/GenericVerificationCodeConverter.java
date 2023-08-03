package by.it_academy.user_service.service.support.converters;

import by.it_academy.user_service.core.dto.VerificationCodeCreateDto;
import by.it_academy.user_service.dao.entity.VerificationCode;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GenericVerificationCodeConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {

        HashSet<ConvertiblePair> pairs = new HashSet<>();

        pairs.add(new ConvertiblePair(VerificationCodeCreateDto.class, VerificationCode.class));

        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() == VerificationCode.class) {
            return source;
        }


        VerificationCodeCreateDto dto = (VerificationCodeCreateDto) source;

        VerificationCode verificationCode = new VerificationCode();

        verificationCode.setCode(dto.getCode());
        verificationCode.setUuid(UUID.randomUUID());
        verificationCode.setUser(dto.getUser());
        LocalDateTime now = LocalDateTime.now();
        verificationCode.setDtCreate(now);
        verificationCode.setDtUpdate(now);

        return verificationCode;
    }
}
