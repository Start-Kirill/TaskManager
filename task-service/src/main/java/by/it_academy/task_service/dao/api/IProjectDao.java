package by.it_academy.task_service.dao.api;

import by.it_academy.task_service.core.enums.ProjectStatus;
import by.it_academy.task_service.dao.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.UUID;

public interface IProjectDao extends
        JpaRepository<Project, UUID>,
        ListPagingAndSortingRepository<Project, UUID> {

    Page<Project> findAllByStatus(Pageable pageable, ProjectStatus status);

    Page<Project> findAllByManagerOrStaff(Pageable pageable, UUID manager, UUID staff);

    Page<Project> findAllByManagerOrStaffAndStatus(Pageable pageable, UUID manager, UUID staff, ProjectStatus status);


}
