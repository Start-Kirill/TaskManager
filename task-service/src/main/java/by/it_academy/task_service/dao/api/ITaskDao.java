package by.it_academy.task_service.dao.api;

import by.it_academy.task_service.dao.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.UUID;

public interface ITaskDao extends
        JpaRepository<Project, UUID>,
        ListPagingAndSortingRepository<Project, UUID> {
}

