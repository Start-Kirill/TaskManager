package by.it_academy.task_service.dao.api;

import by.it_academy.task_service.core.enums.TaskStatus;
import by.it_academy.task_service.dao.entity.Project;
import by.it_academy.task_service.dao.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskDao extends
        JpaRepository<Task, UUID>,
        ListPagingAndSortingRepository<Task, UUID>,
        JpaSpecificationExecutor<Task> {
}

