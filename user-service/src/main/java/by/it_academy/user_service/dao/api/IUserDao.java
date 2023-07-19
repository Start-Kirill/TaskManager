package by.it_academy.user_service.dao.api;

import by.it_academy.user_service.dao.entity.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface IUserDao extends ListCrudRepository<User, UUID> {
}
