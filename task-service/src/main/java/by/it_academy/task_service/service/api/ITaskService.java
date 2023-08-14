package by.it_academy.task_service.service.api;

import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_service.core.dto.PageTasksCreateDto;
import by.it_academy.task_service.core.dto.TaskCreateDto;
import by.it_academy.task_service.core.enums.TaskStatus;
import by.it_academy.task_service.dao.entity.Task;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ITaskService extends ICRUDService<Task, TaskCreateDto> {

    Task update(UUID uuid, LocalDateTime dtUpdate, TaskStatus status);

    CustomPage<Task> get(PageTasksCreateDto dto);

}
