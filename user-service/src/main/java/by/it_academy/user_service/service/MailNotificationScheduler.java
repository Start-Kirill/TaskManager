package by.it_academy.user_service.service;

import by.it_academy.user_service.core.dto.VerificationUpdateDto;
import by.it_academy.user_service.core.enums.VerificationStatus;
import by.it_academy.user_service.dao.entity.Verification;
import by.it_academy.user_service.service.api.IMailNotificationScheduler;
import by.it_academy.user_service.service.api.INotificationService;
import by.it_academy.user_service.service.api.IVerificationService;
import by.it_academy.user_service.service.exceptions.commonInternal.FailedSendingMailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailNotificationScheduler implements IMailNotificationScheduler {

    private final IVerificationService verificationService;

    private final INotificationService notificationService;

    public MailNotificationScheduler(IVerificationService verificationService, INotificationService notificationService) {
        this.verificationService = verificationService;
        this.notificationService = notificationService;
    }

    @Scheduled(fixedDelay = 10000)
    @Override
    public void execute() {
        List<Verification> verifications = this.verificationService.get(VerificationStatus.WAIT);
        if (verifications != null && !verifications.isEmpty()) {
            for (Verification v : verifications) {
                VerificationUpdateDto verificationUpdateDto = new VerificationUpdateDto(v.getUser(), v.getUrl(), v.getCode(), VerificationStatus.SENT, v.getAttempt() + 1);
                try {
                    String verificationUrl = buildVerificationUrl(v.getUrl(), v.getUser().getMail(), v.getCode());
                    this.notificationService.sendVerificationUrl(v.getUser().getMail(), "Verification", v.getUser().getFio(), verificationUrl);
                } catch (FailedSendingMailException ex) {
                    if (v.getAttempt() >= 2) {
                        verificationUpdateDto.setStatus(VerificationStatus.ERROR);
                    } else {
                        verificationUpdateDto.setStatus(VerificationStatus.WAIT);
                    }
                } finally {
                    this.verificationService.update(verificationUpdateDto, v.getUuid(), v.getDtUpdate());
                }
            }
        }
    }

    private String buildVerificationUrl(String initUrl, String mail, String code) {

        StringBuilder text = new StringBuilder();

        text.append(initUrl)
                .append("?code=")
                .append(code)
                .append("&mail=")
                .append(mail);

        return text.toString();
    }
}
