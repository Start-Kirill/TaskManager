package by.it_academy.task_service.service;

import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.enums.EssenceType;
import by.it_academy.task_manager_common.enums.UserRole;
import by.it_academy.task_manager_common.exceptions.common.VersionsNotMatchException;
import by.it_academy.task_manager_common.exceptions.commonInternal.GeneratedDataNotCorrectException;
import by.it_academy.task_manager_common.exceptions.commonInternal.InternalServerErrorException;
import by.it_academy.task_manager_common.exceptions.commonInternal.UnknownConstraintException;
import by.it_academy.task_manager_common.exceptions.structured.NotCorrectPageDataException;
import by.it_academy.task_service.core.dto.PageTasksCreateDto;
import by.it_academy.task_service.core.dto.ProjectRef;
import by.it_academy.task_service.core.dto.TaskCreateDto;
import by.it_academy.task_service.core.dto.UserRef;
import by.it_academy.task_service.core.enums.TaskStatus;
import by.it_academy.task_service.dao.api.ITaskDao;
import by.it_academy.task_service.dao.entity.Project;
import by.it_academy.task_service.dao.entity.Task;
import by.it_academy.task_service.service.api.IAuditClientService;
import by.it_academy.task_service.service.api.IProjectService;
import by.it_academy.task_service.service.api.ITaskService;
import by.it_academy.task_service.service.exceptions.common.SuchProjectNotExistsException;
import by.it_academy.task_service.service.exceptions.common.SuchTaskNotExistsException;
import by.it_academy.task_service.service.exceptions.security.UserAccessDeniedException;
import by.it_academy.task_service.service.exceptions.structured.NotValidTaskBodyException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TaskService implements ITaskService {

    private static final String PROJECT_PARAM_NAME = "project";

    private static final String TITLE_PARAM_NAME = "title";

    private static final String PAGE_FIELD_NAME = "page";

    private static final String SIZE_FIELD_NAME = "size";

    private static final String IMPLEMENTER_PARAM_NAME = "implementer";

    private static final String UNIQUE_UUID_CONSTRAINT_NAME = "task_pkey";

    private final ITaskDao taskDao;

    private final IProjectService projectService;

    private final IAuditClientService auditService;

    private final ConversionService conversionService;

    private final UserHolder userHolder;

    public TaskService(ITaskDao taskDao,
                       IProjectService projectService,
                       IAuditClientService auditClientService,
                       ConversionService conversionService,
                       UserHolder userHolder) {
        this.taskDao = taskDao;
        this.projectService = projectService;
        this.auditService = auditClientService;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
    }

    @Transactional
    @Override
    public Task create(TaskCreateDto dto) {

        validate(dto);

        Task task = this.conversionService.convert(dto, Task.class);

        task.setProject(this.projectService.get(dto.getProject().getUuid()));
        task.setUuid(UUID.randomUUID());
        LocalDateTime now = LocalDateTime.now();
        task.setDtCreate(now);
        task.setDtUpdate(now);

        try {
            Task save = this.taskDao.saveAndFlush(task);
            this.auditService.save(userHolder.getUser(), save.getUuid(), "Task was created", EssenceType.TASK);
            return save;
        } catch (DataIntegrityViolationException ex) {
            if (ex.contains(ConstraintViolationException.class)) {
                String constraintName = ((ConstraintViolationException) ex.getCause()).getConstraintName();
                if (UNIQUE_UUID_CONSTRAINT_NAME.equals(constraintName)) {
                    throw new GeneratedDataNotCorrectException(List.of(new ErrorResponse(ErrorType.ERROR, "Internal failure of server. Duplicate uuid was generated. Repeat request or contact administrator")));
                }
                throw new UnknownConstraintException(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
            }
            throw new InternalServerErrorException(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
        } catch (OptimisticLockingFailureException ex) {
            throw new InternalServerErrorException(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
        }
    }

    @Transactional
    @Override
    public Task update(TaskCreateDto dto, UUID uuid, LocalDateTime dtUpdate) {

        validate(dto);

        Task task = get(uuid);

        if (!task.getDtUpdate().truncatedTo(ChronoUnit.MILLIS).equals(dtUpdate)) {
            throw new VersionsNotMatchException(List.of(new ErrorResponse(ErrorType.ERROR, "Task date updates (versions) don't match. Get up-to-date task")));
        }

        task.setTitle(dto.getTitle());
        task.setStatus(dto.getStatus());
        task.setDescription(dto.getDescription());
        task.setImplementer(dto.getImplementer().getUuid());
        task.setProject(this.projectService.get(dto.getProject().getUuid()));

        try {
            Task save = this.taskDao.saveAndFlush(task);
            this.auditService.save(userHolder.getUser(), save.getUuid(), "Task was updated", EssenceType.TASK);
            return save;
        } catch (DataIntegrityViolationException ex) {
            if (ex.contains(ConstraintViolationException.class)) {
                throw new UnknownConstraintException(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
            }
            throw new InternalServerErrorException(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
        } catch (OptimisticLockingFailureException ex) {
            throw new VersionsNotMatchException(List.of(new ErrorResponse(ErrorType.ERROR, "Task date updates (versions) don't match. Get up-to-date task")));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Task get(UUID uuid) {
        if (!this.taskDao.existsById(uuid)) {
            throw new SuchTaskNotExistsException(List.of(new ErrorResponse(ErrorType.ERROR, "Such task does not exist")));
        }
        Task task = this.taskDao.findById(uuid).orElseThrow();

        checkAccessibility(task.getProject().getUuid());

        return task;
    }

    @Transactional
    @Override
    public Task update(UUID uuid, LocalDateTime dtUpdate, TaskStatus status) {

        Task task = get(uuid);

        checkAccessibility(task.getProject().getUuid());

        if (!task.getDtUpdate().truncatedTo(ChronoUnit.MILLIS).equals(dtUpdate)) {
            throw new VersionsNotMatchException(List.of(new ErrorResponse(ErrorType.ERROR, "Task date updates (versions) don't match. Get up-to-date task")));
        }

        task.setStatus(status);

        try {
            Task save = this.taskDao.saveAndFlush(task);
            this.auditService.save(userHolder.getUser(), save.getUuid(), "Task status was updated", EssenceType.TASK);
            return save;
        } catch (DataIntegrityViolationException ex) {
            if (ex.contains(ConstraintViolationException.class)) {
                throw new UnknownConstraintException(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
            }
            throw new InternalServerErrorException(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
        } catch (OptimisticLockingFailureException ex) {
            throw new VersionsNotMatchException(List.of(new ErrorResponse(ErrorType.ERROR, "Task date updates (versions) don't match. Get up-to-date task")));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public CustomPage<Task> get(PageTasksCreateDto dto) {

        validate(dto);

        UserDetailsImpl user = this.userHolder.getUser();

        Specification<Task> specification = Specification.where(null);

        if (!UserRole.ADMIN.equals(user.getRole())) {
            specification = specification.and(TaskSpecifications.findByManager(user.getUuid()))
                    .or(TaskSpecifications.findByStaff(user.getUuid()));
        }

        List<UUID> implementers = dto.getImplementers();
        if (implementers != null && !implementers.isEmpty()) {
            specification = specification.and(TaskSpecifications.findByImplementerIn(implementers));
        }

        List<UUID> projects = dto.getProjects();
        if (projects != null && !projects.isEmpty()) {
            specification = specification.and(TaskSpecifications.findByProjectIn(this.projectService.findAllByIdIn(projects)));
        }

        List<TaskStatus> statuses = dto.getStatuses();
        if (statuses != null && !statuses.isEmpty()) {
            specification = specification.and(TaskSpecifications.findByStatusIn(statuses));
        }

        PageRequest pageRequest = PageRequest.of(dto.getPage(), dto.getSize());
        Page<Task> taskPage = taskPage = this.taskDao.findAll(specification, pageRequest);


        CustomPage<Task> customPage = this.conversionService.convert(taskPage, CustomPage.class);
        customPage.setSize(dto.getSize());
        customPage.setNumber(dto.getPage());

        return customPage;
    }


    private void validate(TaskCreateDto dto) {

        Map<String, String> errors = new HashMap<>();

        ProjectRef projectRef = dto.getProject();
        if (projectRef == null) {
            errors.put(PROJECT_PARAM_NAME, "Project is missing");
        } else if (projectRef.getUuid() == null) {
            errors.put(PROJECT_PARAM_NAME, "Not enough data to add task");
        }

        String title = dto.getTitle();
        if (title == null) {
            errors.put(TITLE_PARAM_NAME, "Title is missing");
        } else if ("".equals(title)) {
            errors.put(TITLE_PARAM_NAME, "Title not must to be empty");
        }

        if (!errors.isEmpty()) {
            throw new NotValidTaskBodyException(errors);
        }

        checkAccessibility(projectRef.getUuid());

        UserRef implementer = dto.getImplementer();
        if (implementer != null && implementer.getUuid() == null) {
            errors.put(IMPLEMENTER_PARAM_NAME, "Not enough data to add task");
        }
        try {
            if (dto.getImplementer() != null) {
                Project project = this.projectService.get(dto.getProject().getUuid());
                Set<UUID> staff = project.getStaff();
                staff.add(project.getManager());
                if (!staff.contains(dto.getImplementer().getUuid())) {
                    errors.put(IMPLEMENTER_PARAM_NAME, "Such user does not participate in this project");
                }
            }
        } catch (SuchProjectNotExistsException ex) {
            errors.put(PROJECT_PARAM_NAME, "Such project does not exists");
        }

        if (!errors.isEmpty()) {
            throw new NotValidTaskBodyException(errors);
        }
    }


    private void validate(PageTasksCreateDto dto) {
        Map<String, String> errors = new HashMap<>();

        Integer page = dto.getPage();
        Integer size = dto.getSize();

        if (page == null) {
            errors.put(PAGE_FIELD_NAME, "Page is missing");
        } else if (page < 0) {
            errors.put(PAGE_FIELD_NAME, "Page must not to be negative value");
        }

        if (size == null) {
            errors.put(SIZE_FIELD_NAME, "Size is missing");
        } else if (size < 1) {
            errors.put(SIZE_FIELD_NAME, "Size must not to be less than 1");
        }

        if (!errors.isEmpty()) {
            throw new NotCorrectPageDataException(errors);
        }
    }

    private void checkAccessibility(UUID projectRef) {
        Project project = this.projectService.get(projectRef);
        Set<UUID> staff = project.getStaff();
        staff.add(project.getManager());

        UserDetailsImpl user = this.userHolder.getUser();
        if (!UserRole.ADMIN.equals(user.getRole()) && !staff.contains(user.getUuid())) {
            throw new UserAccessDeniedException();
        }
    }
}
