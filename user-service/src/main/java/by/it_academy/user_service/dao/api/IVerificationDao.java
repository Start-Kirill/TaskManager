package by.it_academy.user_service.dao.api;

import by.it_academy.user_service.core.enums.VerificationStatus;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.dao.entity.Verification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface IVerificationDao extends CrudRepository<Verification, UUID> {
    Verification findByUser(User user);

    boolean existsByUser(User user);

    List<Verification> findAllByStatus(VerificationStatus status);
}
