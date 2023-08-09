package by.it_academy.task_service.service.api;

import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_service.core.dto.ProjectCreateDto;
import by.it_academy.task_service.dao.entity.Project;

public interface IProjectService extends ICRUDService<Project, ProjectCreateDto> {

    CustomPage<Project> get(Integer page, Integer size, boolean archived);
}
