package by.it_academy.user_service.service.api;

public interface INotificationService {
    void send(String target, String subject, String message);

    void sendVerificationUrl(String target, String subject, String recipientName, String url);
}
