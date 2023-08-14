package by.it_academy.task_service.dao.api;

import by.it_academy.task_service.core.enums.TaskStatus;
import by.it_academy.task_service.dao.entity.Project;
import by.it_academy.task_service.dao.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskDao extends
        JpaRepository<Task, UUID>,
        ListPagingAndSortingRepository<Task, UUID> {

    Page<Task> findAllByProjectIn(Pageable pageable, List<Project> projects);

    Page<Task> findAllByImplementerIn(Pageable pageable, List<UUID> implementers);

    Page<Task> findAllByStatusIn(Pageable pageable, List<TaskStatus> statuses);

    Page<Task> findAllByProjectInAndImplementerIn(Pageable pageable, List<Project> projects, List<UUID> implementers);

    Page<Task> findAllByProjectInAndStatusIn(Pageable pageable, List<Project> projects, List<TaskStatus> statuses);

    Page<Task> findAllByImplementerInAndStatusIn(Pageable pageable, List<UUID> implementers, List<TaskStatus> statuses);

    Page<Task> findAllByProjectInAndImplementerInAndStatusIn(Pageable pageable, List<Project> projects, List<UUID> implementers, List<TaskStatus> statuses);

    @Query(value = "SELECT t.uuid, t.dt_create, t.dt_update, project_uuid, title, t.description, t.status, implementer FROM app.task t " +
            "INNER JOIN app.project p ON t.project_uuid = p.uuid " +
            "WHERE ?1 = ANY(p.staff) OR ?1 = p.manager", nativeQuery = true)
    Page<Task> findAllByParticipant(Pageable pageable, UUID participant);

    @Query(value = "SELECT t.uuid, t.dt_create, t.dt_update, project_uuid, title, t.description, t.status, implementer FROM app.task t " +
            "INNER JOIN app.project p ON t.project_uuid = p.uuid " +
            "WHERE project_uuid IN ?1 AND (?2 = ANY(p.staff) OR ?2 = p.manager)", nativeQuery = true)
    Page<Task> findAllByProjectInAndByParticipant(Pageable pageable, List<UUID> projects, UUID participant);

    @Query(value = "SELECT t.uuid, t.dt_create, t.dt_update, project_uuid, title, t.description, t.status, implementer FROM app.task t " +
            "INNER JOIN app.project p ON t.project_uuid = p.uuid " +
            "WHERE implementer IN ?1 AND (?2 = ANY(p.staff) OR ?2 = p.manager)", nativeQuery = true)
    Page<Task> findAllByImplementerInAndByParticipant(Pageable pageable, List<UUID> implementers, UUID participant);

    @Query(value = "SELECT t.uuid, t.dt_create, t.dt_update, project_uuid, title, t.description, t.status, implementer FROM app.task t " +
            "INNER JOIN app.project p ON t.project_uuid = p.uuid " +
            "WHERE t.status IN ?1 AND (?2 = ANY(p.staff) OR ?2 = p.manager)", nativeQuery = true)
    Page<Task> findAllByStatusInAndByParticipant(Pageable pageable, List<String> statuses, UUID participant);

    @Query(value = "SELECT t.uuid, t.dt_create, t.dt_update, project_uuid, title, t.description, t.status, implementer FROM app.task t " +
            "INNER JOIN app.project p ON t.project_uuid = p.uuid " +
            "WHERE project_uuid IN ?1 AND implementer IN ?2 AND (?3 = ANY(p.staff) OR ?3 = p.manager)", nativeQuery = true)
    Page<Task> findAllByProjectInAndImplementerInAndByParticipant(Pageable pageable, List<UUID> projects, List<UUID> implementers, UUID participant);

    @Query(value = "SELECT t.uuid, t.dt_create, t.dt_update, project_uuid, title, t.description, t.status, implementer FROM app.task t " +
            "INNER JOIN app.project p ON t.project_uuid = p.uuid " +
            "WHERE project_uuid IN ?1 AND t.status IN ?2 AND (?3 = ANY(p.staff) OR ?3 = p.manager)", nativeQuery = true)
    Page<Task> findAllByProjectInAndStatusInAndByByParticipant(Pageable pageable, List<UUID> projects, List<String> statuses, UUID participant);

    @Query(value = "SELECT t.uuid, t.dt_create, t.dt_update, project_uuid, title, t.description, t.status, implementer FROM app.task t " +
            "INNER JOIN app.project p ON t.project_uuid = p.uuid " +
            "WHERE implementer IN ?1 AND t.status IN ?2 AND (?3 = ANY(p.staff) OR ?3 = p.manager)", nativeQuery = true)
    Page<Task> findAllByImplementerInAndStatusInAndByParticipant(Pageable pageable, List<UUID> implementers, List<String> statuses, UUID participant);

    @Query(value = "SELECT t.uuid, t.dt_create, t.dt_update, project_uuid, title, t.description, t.status, implementer FROM app.task t " +
            "INNER JOIN app.project p ON t.project_uuid = p.uuid " +
            "WHERE project_uuid IN ?1 AND implementer IN ?2 AND t.status IN ?3 AND (?4 = ANY(p.staff) OR ?4 = p.manager)", nativeQuery = true)
    Page<Task> findAllByProjectInAndImplementerInAndStatusInAndByParticipant(Pageable pageable, List<UUID> projects, List<UUID> implementers, List<String> statuses, UUID participant);


}

