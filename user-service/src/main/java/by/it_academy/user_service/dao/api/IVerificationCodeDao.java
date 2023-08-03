package by.it_academy.user_service.dao.api;

import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.dao.entity.VerificationCode;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IVerificationCodeDao extends CrudRepository<VerificationCode, UUID> {
    VerificationCode findByUser(User user);

}