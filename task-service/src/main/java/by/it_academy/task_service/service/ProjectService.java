package by.it_academy.task_service.service;

import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_service.core.dto.ProjectCreateDto;
import by.it_academy.task_service.dao.api.IProjectDao;
import by.it_academy.task_service.dao.entity.Project;
import by.it_academy.task_service.service.api.IAuditClientService;
import by.it_academy.task_service.service.api.IProjectService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ProjectService implements IProjectService {

    private final IProjectDao projectDao;

    private final IAuditClientService auditService;

    private final ConversionService conversionService;

    private final UserHolder userHolder;


    public ProjectService(IAuditClientService auditService,
                          ConversionService conversionService,
                          UserHolder userHolder,
                          IProjectDao projectDao) {
        this.auditService = auditService;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
        this.projectDao = projectDao;
    }

    @Override
    public Project create(ProjectCreateDto dto) {
        return null;
    }

    @Override
    public Project update(ProjectCreateDto dto, UUID uuid, LocalDateTime dtUpdate) {
        return null;
    }

    @Override
    public Project get(UUID uuid) {
        return null;
    }

    @Override
    public CustomPage<Project> get(Integer page, Integer size, boolean archived) {
        return null;
    }
}
