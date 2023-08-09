package by.it_academy.user_service.service.api;

public interface INotificationService {
    void send(String target, String message, String subject);
}
