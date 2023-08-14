package by.it_academy.task_service.service.api;

import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_service.core.dto.ProjectCreateDto;
import by.it_academy.task_service.dao.entity.Project;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface IProjectService extends ICRUDService<Project, ProjectCreateDto> {

    CustomPage<Project> get(Integer page, Integer size, boolean archived);

    List<Project> findAllByIdIn(Collection<UUID> uuid);
}
