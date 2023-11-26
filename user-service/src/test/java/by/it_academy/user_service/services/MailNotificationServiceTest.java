package by.it_academy.user_service.services;

import by.it_academy.user_service.service.MailNotificationService;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MailNotificationServiceTest {

    private static final String TARGET = "test@mail.ru";

    private static final String SUBJECT = "Test";

    private static final String MESSAGE = "hello";

    private static final String RECIPIENT_NAME = "Testovich";

    private static final String URL = "https://wwww.test.com";

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private SpringTemplateEngine thymeleafTemplateEngine;

    @InjectMocks
    private MailNotificationService mailNotificationService;

    @BeforeEach
    public void setUp() {
        final MimeMessage mimeMessage = mock(MimeMessage.class);
        when(this.mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    public void shouldSendMail() {
        assertDoesNotThrow(() -> this.mailNotificationService).send(TARGET, SUBJECT, MESSAGE);
    }

    @Test
    public void shouldSendVerificationUrl() {
        when(this.thymeleafTemplateEngine.process(Mockito.anyString(), Mockito.any(Context.class))).thenReturn(MESSAGE);

        assertDoesNotThrow(() -> this.mailNotificationService.sendVerificationUrl(TARGET, SUBJECT, RECIPIENT_NAME, URL));
    }

}
