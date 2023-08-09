package by.it_academy.task_service.endpoints.web.controllers;

import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_service.core.dto.ProjectCreateDto;
import by.it_academy.task_service.core.dto.ProjectDto;
import by.it_academy.task_service.dao.entity.Project;
import by.it_academy.task_service.service.api.IProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private static final String CONTENT_FIELD_NAME = "content";

    private final IProjectService projectService;

    private final ConversionService conversionService;

    public ProjectController(IProjectService projectService, ConversionService conversionService) {
        this.projectService = projectService;
        this.conversionService = conversionService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProjectCreateDto dto) {
        this.projectService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam(required = false, defaultValue = "0") Integer page,
                                 @RequestParam(required = false, defaultValue = "20") Integer size,
                                 @RequestParam(required = false, defaultValue = "false") boolean archived) {
        CustomPage<Project> projectCustomPage = this.projectService.get(page, size, archived);
        CustomPage<ProjectDto> projectDtoCustomPage = new CustomPage<>();
        BeanUtils.copyProperties(projectCustomPage, projectDtoCustomPage, CONTENT_FIELD_NAME);
        List<ProjectDto> projectDtos = projectCustomPage.getContent().stream().map(p -> this.conversionService.convert(p, ProjectDto.class)).toList();
        projectDtoCustomPage.setContent(projectDtos);

        return ResponseEntity.status(HttpStatus.OK).body(projectDtoCustomPage);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> get(@PathVariable UUID uuid) {
        Project project = this.projectService.get(uuid);
        ProjectDto projectDto = this.conversionService.convert(project, ProjectDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(projectDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@PathVariable UUID uuid,
                                    @PathVariable(name = "dt_update") LocalDateTime dtUpdate,
                                    @RequestBody ProjectCreateDto dto) {
        this.projectService.update(dto, uuid, dtUpdate);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
