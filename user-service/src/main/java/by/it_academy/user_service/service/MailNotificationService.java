package by.it_academy.user_service.service;

import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.user_service.service.api.INotificationService;
import by.it_academy.user_service.service.exceptions.commonInternal.FailedSendingMailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailNotificationService implements INotificationService {

    private final JavaMailSender emailSender;

    public MailNotificationService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void send(String target, String subject, String message) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(target);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message);

            emailSender.send(mimeMessage);
        } catch (MessagingException | MailSendException | MailAuthenticationException e) {
            throw new FailedSendingMailException(e.getCause(), List.of(new ErrorResponse(ErrorType.ERROR, "Failed to send mail. Try to send it later or contact the administrator")));
        }
    }
}
