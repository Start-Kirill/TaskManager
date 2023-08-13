package by.it_academy.user_service.dao.api;

import by.it_academy.user_service.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserDao extends
        JpaRepository<User, UUID>,
        ListPagingAndSortingRepository<User, UUID> {
    Optional<User> findByMail(String mail);

    boolean existsByMail(String mail);

    List<User> findAllByUuidIn(Collection<UUID> uuid);

}
