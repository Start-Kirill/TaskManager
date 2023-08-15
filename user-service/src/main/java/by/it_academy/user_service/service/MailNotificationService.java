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
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


import java.util.HashMap;
import java.util.List;

@Service
public class MailNotificationService implements INotificationService {

    private static final String RECIPIENT_NAME_VAR_NAME = "recipientName";

    private static final String URL_VAR_NAME = "url";

    private static final String SENDER_NAME_VAR_NAME = "senderName";

    private static final String TEMPLATE_NAME = "template-thymeleaf.html";

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine thymeleafTemplateEngine;

    public MailNotificationService(JavaMailSender emailSender, SpringTemplateEngine thymeleafTemplateEngine) {
        this.emailSender = emailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
    }

    @Override
    public void send(String target, String subject, String message) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(target);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message, true);


            emailSender.send(mimeMessage);
        } catch (MessagingException | MailSendException | MailAuthenticationException e) {
            throw new FailedSendingMailException(e.getCause(), List.of(new ErrorResponse(ErrorType.ERROR, "Failed to send mail. Try to send it later or contact the administrator")));
        }
    }

    @Override
    public void sendVerificationUrl(String target, String subject, String recipientName, String url) {
        HashMap<String, Object> variables = new HashMap<>();

        variables.put(RECIPIENT_NAME_VAR_NAME, recipientName);
        variables.put(URL_VAR_NAME, url);
        variables.put(SENDER_NAME_VAR_NAME, "System");

        Context context = new Context();

        context.setVariables(variables);

        String htmlBody = this.thymeleafTemplateEngine.process(TEMPLATE_NAME, context);

        send(target, subject, htmlBody);

    }


}
