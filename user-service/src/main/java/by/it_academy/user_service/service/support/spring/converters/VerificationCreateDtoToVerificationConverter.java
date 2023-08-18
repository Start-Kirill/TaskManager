package by.it_academy.user_service.service.support.spring.converters;

import by.it_academy.user_service.core.dto.VerificationCreateDto;
import by.it_academy.user_service.core.enums.VerificationStatus;
import by.it_academy.user_service.dao.entity.Verification;
import org.springframework.core.convert.converter.Converter;

public class VerificationCreateDtoToVerificationConverter implements Converter<VerificationCreateDto, Verification> {

    @Override
    public Verification convert(VerificationCreateDto source) {
        Verification verification = new Verification();

        verification.setUser(source.getUser());
        verification.setAttempt(0L);
        verification.setStatus(VerificationStatus.WAIT);

        return verification;
    }
}
