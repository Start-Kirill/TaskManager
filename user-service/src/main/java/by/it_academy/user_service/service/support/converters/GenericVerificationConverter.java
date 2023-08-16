package by.it_academy.user_service.service.support.converters;

import by.it_academy.user_service.core.dto.VerificationCreateDto;
import by.it_academy.user_service.core.enums.VerificationStatus;
import by.it_academy.user_service.dao.entity.Verification;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.HashSet;
import java.util.Set;

public class GenericVerificationConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {

        HashSet<ConvertiblePair> pairs = new HashSet<>();

        pairs.add(new ConvertiblePair(VerificationCreateDto.class, Verification.class));

        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() == Verification.class) {
            return source;
        }

        VerificationCreateDto dto = (VerificationCreateDto) source;

        Verification verification = new Verification();

        verification.setUser(dto.getUser());
        verification.setAttempt(0L);
        verification.setStatus(VerificationStatus.WAIT);

        return verification;
    }
}
