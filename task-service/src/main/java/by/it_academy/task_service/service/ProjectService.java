package by.it_academy.task_service.service;

import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.dto.UserDto;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.enums.EssenceType;
import by.it_academy.task_manager_common.enums.UserRole;
import by.it_academy.task_manager_common.exceptions.common.VersionsNotMatchException;
import by.it_academy.task_manager_common.exceptions.commonInternal.FeignErrorException;
import by.it_academy.task_manager_common.exceptions.commonInternal.GeneratedDataNotCorrectException;
import by.it_academy.task_manager_common.exceptions.commonInternal.InternalServerErrorException;
import by.it_academy.task_manager_common.exceptions.commonInternal.UnknownConstraintException;
import by.it_academy.task_manager_common.exceptions.structured.NotCorrectPageDataException;
import by.it_academy.task_service.core.dto.ProjectCreateDto;
import by.it_academy.task_service.core.dto.UserRef;
import by.it_academy.task_service.core.enums.ProjectStatus;
import by.it_academy.task_service.dao.api.IProjectDao;
import by.it_academy.task_service.dao.entity.Project;
import by.it_academy.task_service.service.api.IAuditClientService;
import by.it_academy.task_service.service.api.IProjectService;
import by.it_academy.task_service.service.api.IUserClientService;
import by.it_academy.task_service.service.exceptions.common.SuchProjectNotExistsException;
import by.it_academy.task_service.service.exceptions.security.ManagerAccessDeniedException;
import by.it_academy.task_service.service.exceptions.security.UserAccessDeniedException;
import by.it_academy.task_service.service.exceptions.structured.ManagerNotExistsException;
import by.it_academy.task_service.service.exceptions.structured.NotUniqueProjectNameException;
import by.it_academy.task_service.service.exceptions.structured.NotValidProjectBodyException;
import by.it_academy.task_service.service.exceptions.structured.StaffNotExistsException;
import feign.FeignException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService implements IProjectService {

    private static final String PAGE_FIELD_NAME = "page";

    private static final String SIZE_FIELD_NAME = "size";

    private static final String MANAGER_FIELD_NAME = "manager";

    private static final String STAFF_FIELD_NAME = "staff";

    private static final String NAME_FIELD_NAME = "name";

    private static final String UNIQUE_NAME_CONSTRAINT_NAME = "project_name_key";

    private static final String UNIQUE_UUID_CONSTRAINT_NAME = "project_pkey";

    private static final String USER_NOT_EXISTS_MESSAGE = "Such user doesn't exist";

    private final IProjectDao projectDao;

    private final IAuditClientService auditService;

    private final IUserClientService userClientService;

    private final ConversionService conversionService;

    private final UserHolder userHolder;


    public ProjectService(IAuditClientService auditService,
                          ConversionService conversionService,
                          UserHolder userHolder,
                          IProjectDao projectDao,
                          IUserClientService userClientService) {
        this.auditService = auditService;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
        this.projectDao = projectDao;
        this.userClientService = userClientService;
    }

    @Transactional
    @Override
    public Project create(ProjectCreateDto dto) {

        validate(dto);

        Project project = this.conversionService.convert(dto, Project.class);

        project.setUuid(UUID.randomUUID());
        LocalDateTime now = LocalDateTime.now();
        project.setDtCreate(now);
        project.setDtUpdate(now);

        try {
            Project save = this.projectDao.saveAndFlush(project);
            this.auditService.save(userHolder.getUser(), save.getUuid(), "Project was created", EssenceType.PROJECT);
            return save;
        } catch (DataIntegrityViolationException ex) {
            if (ex.contains(ConstraintViolationException.class)) {
                String constraintName = ((ConstraintViolationException) ex.getCause()).getConstraintName();
                if (UNIQUE_NAME_CONSTRAINT_NAME.equals(constraintName)) {
                    throw new NotUniqueProjectNameException(Map.of(NAME_FIELD_NAME, "Project with such name already exists"));
                } else if (UNIQUE_UUID_CONSTRAINT_NAME.equals(constraintName)) {
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
    public Project update(ProjectCreateDto dto, UUID uuid, LocalDateTime dtUpdate) {

        validate(dto);

        Project project = this.get(uuid);

        UserDetailsImpl user = this.userHolder.getUser();
        if (UserRole.MANAGER.equals(user.getRole())) {
            if (!user.getUuid().equals(project.getManager())) {
                throw new ManagerAccessDeniedException();
            }
        }

        if (!project.getDtUpdate().truncatedTo(ChronoUnit.MILLIS).equals(dtUpdate)) {
            throw new VersionsNotMatchException(List.of(new ErrorResponse(ErrorType.ERROR, "Project date updates (versions) don't match. Get up-to-date project")));
        }

        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setManager(dto.getManager().getUuid());
        project.setStaff(dto.getStaff().stream().map(UserRef::getUuid).collect(Collectors.toSet()));
        project.setStatus(dto.getStatus());

        try {
            Project save = this.projectDao.saveAndFlush(project);
            this.auditService.save(userHolder.getUser(), save.getUuid(), "Project was updated", EssenceType.PROJECT);
            return save;
        } catch (DataIntegrityViolationException ex) {
            if (ex.contains(ConstraintViolationException.class)) {
                String constraintName = ((ConstraintViolationException) ex.getCause()).getConstraintName();
                if (UNIQUE_NAME_CONSTRAINT_NAME.equals(constraintName)) {
                    throw new NotUniqueProjectNameException(Map.of(NAME_FIELD_NAME, "Project with such name already exists"));
                }
                throw new UnknownConstraintException(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
            }
            throw new InternalServerErrorException(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
        } catch (OptimisticLockingFailureException ex) {
            throw new VersionsNotMatchException(List.of(new ErrorResponse(ErrorType.ERROR, "Project date updates (versions) don't match. Get up-to-date project")));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Project get(UUID uuid) {
        if (!this.projectDao.existsById(uuid)) {
            throw new SuchProjectNotExistsException(List.of(new ErrorResponse(ErrorType.ERROR, "Such project does not exist")));
        }
        UserDetailsImpl user = this.userHolder.getUser();
        if (UserRole.ADMIN.equals(user.getRole())) {
            return this.projectDao.getReferenceById(uuid);
        }

        Project project = this.projectDao.getReferenceById(uuid);
        Set<UUID> staff = project.getStaff();
        staff.add(project.getManager());

        if (!staff.contains(user.getUuid())) {
            throw new UserAccessDeniedException();
        }

        return project;
    }

    @Transactional(readOnly = true)
    @Override
    public CustomPage<Project> get(Integer page, Integer size, boolean archived) {
        validate(page, size);
        Page<Project> projectPage = null;
        PageRequest pageRequest = PageRequest.of(page, size);
        UserDetailsImpl user = userHolder.getUser();
        CustomPage<Project> customPage = null;
        if (UserRole.ADMIN.equals(user.getRole())) {
            if (archived) {
                projectPage = this.projectDao.findAll(pageRequest);
            } else {
                projectPage = this.projectDao.findAllByStatus(pageRequest, ProjectStatus.ACTIVE);
            }
        } else {
            if (archived) {
                projectPage = this.projectDao.findAllByManagerOrStaff(pageRequest, user.getUuid(), user.getUuid());
            } else {
                projectPage = this.projectDao.findAllByManagerOrStaffAndStatus(pageRequest, user.getUuid(), user.getUuid(), ProjectStatus.ACTIVE);
            }
        }
        customPage = this.conversionService.convert(projectPage, CustomPage.class);
        customPage.setSize(size);
        customPage.setNumber(page);

        return customPage;
    }

    @Override
    public List<Project> findAllByIdIn(Collection<UUID> projects) {
        return this.projectDao.findAllById(projects);
    }


    private void validate(ProjectCreateDto dto) {
        UserRef manager = dto.getManager();
        Map<String, String> errors = new HashMap<>();
        if (manager != null) {
            if (manager.getUuid() == null) {
                errors.put(MANAGER_FIELD_NAME, "Not enough data to add manager");
            } else {
                UserDto userDto = null;
                try {
                    userDto = this.userClientService.get(userHolder.getUser(), manager.getUuid());
                } catch (FeignErrorException ex) {
                    FeignException cause = (FeignException) ex.getCause();
                    String message = cause.getMessage();
                    if (message != null && message.contains(USER_NOT_EXISTS_MESSAGE)) {
                        throw new ManagerNotExistsException(Map.of(MANAGER_FIELD_NAME, "Such manager does not exist"));
                    }
                    throw ex;
                }

                if (!UserRole.MANAGER.equals(userDto.getRole())) {
                    errors.put(MANAGER_FIELD_NAME, "User role differ from MANAGER");
                }
            }
        }

        List<UserRef> staff = dto.getStaff();
        if (staff != null) {
            Set<UUID> users = staff.stream().map(UserRef::getUuid).collect(Collectors.toSet());
            List<UserDto> userDtos = null;

            userDtos = this.userClientService.get(this.userHolder.getUser(), users);

            if (userDtos.size() != users.size()) {
                throw new StaffNotExistsException(Map.of(STAFF_FIELD_NAME, "One or more participants not exist"));
            }
        }


        if (!errors.isEmpty()) {
            throw new NotValidProjectBodyException(errors);
        }

    }

    private void validate(Integer page, Integer size) {
        Map<String, String> errors = new HashMap<>();

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
}
