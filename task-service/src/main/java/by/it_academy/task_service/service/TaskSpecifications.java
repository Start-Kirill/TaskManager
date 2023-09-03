package by.it_academy.task_service.service;

import by.it_academy.task_service.core.enums.TaskStatus;
import by.it_academy.task_service.dao.entity.Project;
import by.it_academy.task_service.dao.entity.Task;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public class TaskSpecifications {

    public static final String NAME_FIELD_PROJECT = "project";

    public static final String NAME_FIELD_IMPLEMENTER = "implementer";

    public static final String NAME_FIELD_STATUS = "status";

    public static final String NAME_FIELD_MANAGER = "manager";

    public static final String STAFF_FIELD_MANAGER = "staff";

    public static Specification<Task> findByManager(UUID participant) {
        return (root, query, criteriaBuilder) -> {
            Join<Task, Project> taskProjectJoin = root.join(NAME_FIELD_PROJECT);
            return criteriaBuilder.equal(taskProjectJoin.get(NAME_FIELD_MANAGER), participant);
        };
    }

    public static Specification<Task> findByStaff(UUID participant) {
        return (root, query, criteriaBuilder) -> {
            Join<Task, Project> taskProjectJoin = root.join(NAME_FIELD_PROJECT);
            return criteriaBuilder.isMember(participant, taskProjectJoin.get(STAFF_FIELD_MANAGER));
        };
    }

    public static Specification<Task> findByProjectIn(List<Project> projects) {
        return (root, query, cb) -> root.get(NAME_FIELD_PROJECT).in(projects);
    }

    public static Specification<Task> findByImplementerIn(List<UUID> implementers) {
        return (root, query, cb) -> root.get(NAME_FIELD_IMPLEMENTER).in(implementers);
    }

    public static Specification<Task> findByStatusIn(List<TaskStatus> statuses) {
        return (root, query, cb) -> root.get(NAME_FIELD_STATUS).in(statuses);
    }

}
