package by.it_academy.audit_service.dao.api;

import by.it_academy.audit_service.dao.entity.Audit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IAuditDao extends
        CrudRepository<Audit, UUID>,
        ListPagingAndSortingRepository<Audit, UUID> {

    List<Audit> findByUserUuidAndDtCreateBetween(UUID user, LocalDateTime from, LocalDateTime to);

}
