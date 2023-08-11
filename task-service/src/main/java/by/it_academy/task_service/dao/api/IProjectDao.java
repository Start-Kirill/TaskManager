package by.it_academy.task_service.dao.api;

import by.it_academy.task_service.core.enums.ProjectStatus;
import by.it_academy.task_service.dao.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.UUID;

public interface IProjectDao extends
        JpaRepository<Project, UUID>,
        ListPagingAndSortingRepository<Project, UUID> {

    Page<Project> findAllByStatus(Pageable pageable, ProjectStatus status);

    @Query(value = "SELECT * FROM app.project p WHERE ?1 = ANY(p.staff) OR ?1 = p.manager", nativeQuery = true)
    Page<Project> findAllByParticipant(Pageable pageable, UUID uuid);

    @Query(value = "SELECT * FROM app.project p WHERE (?1 = ANY(p.staff) OR ?1 = p.manager) AND ?2 = p.status", nativeQuery = true)
    Page<Project> findAllByParticipantAndStatus(Pageable pageable, UUID uuid, String status);
}
