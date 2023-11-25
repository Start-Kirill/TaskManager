package by.it_academy.user_service.services;

import by.it_academy.user_service.config.property.AppProperty;
import by.it_academy.user_service.core.dto.VerificationCreateDto;
import by.it_academy.user_service.core.dto.VerificationUpdateDto;
import by.it_academy.user_service.core.enums.VerificationStatus;
import by.it_academy.user_service.dao.api.IVerificationDao;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.dao.entity.Verification;
import by.it_academy.user_service.service.VerificationService;
import by.it_academy.user_service.service.exceptions.common.VerificationCodeNotExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class VerificationServiceTest {

    @Mock
    private ConversionService conversionService;

    @Mock
    private AppProperty property;

    @Mock
    private AppProperty.Verification verificationProp;

    @Mock
    private IVerificationDao verificationDao;


    private VerificationService verificationService;

    @BeforeEach
    public void setUp() {
        when(property.getVerification()).thenReturn(verificationProp);
        this.verificationService = new VerificationService(this.verificationDao, this.conversionService, this.property);
    }

    @Test
    public void shouldSaveUser() {
        final Verification verification = mock(Verification.class);
        when(this.conversionService.convert(Mockito.any(VerificationCreateDto.class), Mockito.eq(Verification.class)))
                .thenReturn(verification);
        when(this.verificationProp.getUrl()).thenReturn("http://Test/verification");
        when(this.verificationDao.save(Mockito.any(Verification.class))).then(m -> m.getArgument(0));

        final VerificationCreateDto verificationCreateDto = mock(VerificationCreateDto.class);
        Verification actual = this.verificationService.save(verificationCreateDto);

        assertNotNull(actual);
        assertEquals(verification, actual);
    }

    @Test
    public void shouldUpdateUser() {
        final UUID uuid = mock(UUID.class);
        final LocalDateTime dtUpdate = mock(LocalDateTime.class);
        final Verification verification = mock(Verification.class);
        when(verification.getDtUpdate()).thenReturn(dtUpdate);
        when(this.verificationDao.existsById(uuid)).thenReturn(true);
        when(this.verificationDao.findById(uuid)).thenReturn(Optional.of(verification));
        when(this.verificationDao.save(verification)).thenReturn(verification);

        final VerificationUpdateDto verificationUpdateDto = mock(VerificationUpdateDto.class);
        Verification update = this.verificationService.update(verificationUpdateDto, uuid, dtUpdate);

        assertNotNull(update);
        assertEquals(verification, update);
    }

    @Test
    public void shouldGetByUser() {
        final User user = mock(User.class);
        final Verification verification = mock(Verification.class);
        when(this.verificationDao.existsByUser(user)).thenReturn(true);
        when(this.verificationDao.findByUser(user)).thenReturn(verification);

        Verification actual = this.verificationService.get(user);

        assertNotNull(actual);
        assertEquals(verification, actual);
    }

    @Test
    public void shouldThrowWhileGetByUser() {
        final User user = mock(User.class);
        when(this.verificationDao.existsByUser(user)).thenReturn(false);

        assertThrows(VerificationCodeNotExistsException.class, () -> this.verificationService.get(user));
    }

    @Test
    public void shouldGetByStatus() {
        final ArrayList<Verification> verifications = new ArrayList<>();
        VerificationStatus verificationStatus = mock(VerificationStatus.class);
        when(this.verificationDao.findAllByStatus(verificationStatus)).thenReturn(verifications);

        List<Verification> actual = this.verificationService.get(verificationStatus);

        assertNotNull(actual);
        assertEquals(actual, verifications);
    }

    @Test
    public void shouldGetByUuid() {
        final UUID uuid = mock(UUID.class);
        final Verification verification = mock(Verification.class);
        when(this.verificationDao.existsById(uuid)).thenReturn(true);
        when(this.verificationDao.findById(uuid)).thenReturn(Optional.of(verification));

        Verification actual = this.verificationService.get(uuid);

        assertNotNull(actual);
        assertEquals(actual, verification);
    }

    @Test
    public void shouldTrowWhileGetByUuid() {
        final UUID uuid = mock(UUID.class);
        when(this.verificationDao.existsById(uuid)).thenReturn(false);

        assertThrows(VerificationCodeNotExistsException.class, () -> this.verificationService.get(uuid));
    }

    @Test
    public void shouldGenerateCode() {
        assertNotNull(this.verificationService.generateCode());
    }

}
