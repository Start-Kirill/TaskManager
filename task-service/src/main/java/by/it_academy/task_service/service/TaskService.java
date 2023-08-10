package by.it_academy.task_service.service;

import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_manager_common.enums.UserStatus;
import by.it_academy.task_service.core.dto.PageTasksCreateDto;
import by.it_academy.task_service.core.dto.TaskCreateDto;
import by.it_academy.task_service.dao.api.ITaskDao;
import by.it_academy.task_service.dao.entity.Task;
import by.it_academy.task_service.service.api.IAuditClientService;
import by.it_academy.task_service.service.api.IProjectService;
import by.it_academy.task_service.service.api.ITaskService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TaskService implements ITaskService {

    private final ITaskDao taskDao;

    private final IProjectService projectService;

    private final IAuditClientService auditClientService;

    private final ConversionService conversionService;

    private final UserHolder userHolder;

    public TaskService(ITaskDao taskDao,
                       IProjectService projectService,
                       IAuditClientService auditClientService,
                       ConversionService conversionService,
                       UserHolder userHolder) {
        this.taskDao = taskDao;
        this.projectService = projectService;
        this.auditClientService = auditClientService;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
    }

    @Override
    public Task create(TaskCreateDto dto) {
        return null;
    }

    @Override
    public Task update(TaskCreateDto dto, UUID uuid, LocalDateTime dtUpdate) {
        return null;
    }

    @Override
    public Task get(UUID uuid) {
        return null;
    }

    @Override
    public Task update(UUID uuid, LocalDateTime dtUpdate, UserStatus status) {
        return null;
    }

    @Override
    public CustomPage<Task> get(PageTasksCreateDto dto) {
        return null;
    }
}
