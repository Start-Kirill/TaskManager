package by.it_academy.task_service.endpoints.web.controllers;

import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_service.core.dto.PageTasksCreateDto;
import by.it_academy.task_service.core.dto.TaskCreateDto;
import by.it_academy.task_service.core.dto.TaskDto;
import by.it_academy.task_service.core.enums.TaskStatus;
import by.it_academy.task_service.dao.entity.Task;
import by.it_academy.task_service.service.api.ITaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    private static final String CONTENT_FIELD_NAME = "content";

    private final ITaskService taskService;

    private final ConversionService conversionService;


    public TaskController(ITaskService taskService, ConversionService conversionService) {
        this.taskService = taskService;
        this.conversionService = conversionService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TaskCreateDto dto) {
        this.taskService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam Integer page,
                                 @RequestParam Integer size,
                                 @RequestParam(required = false) List<UUID> project,
                                 @RequestParam(required = false) List<UUID> implementer,
                                 @RequestParam(required = false) List<TaskStatus> status) {
        PageTasksCreateDto pageTasksCreateDto = new PageTasksCreateDto(page, size, project, implementer, status);
        CustomPage<Task> projectCustomPage = this.taskService.get(pageTasksCreateDto);
        CustomPage<TaskDto> projectDtoCustomPage = new CustomPage<>();
        BeanUtils.copyProperties(projectCustomPage, projectDtoCustomPage, CONTENT_FIELD_NAME);
        List<TaskDto> taskDtos = projectCustomPage.getContent().stream().map(t -> this.conversionService.convert(t, TaskDto.class)).toList();
        projectDtoCustomPage.setContent(taskDtos);
        return ResponseEntity.status(HttpStatus.OK).body(projectDtoCustomPage);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> get(@PathVariable UUID uuid) {
        Task task = this.taskService.get(uuid);
        TaskDto taskDto = this.conversionService.convert(task, TaskDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@PathVariable UUID uuid,
                                    @PathVariable LocalDateTime dtUpdate,
                                    @RequestBody TaskCreateDto dto) {
        this.taskService.update(dto, uuid, dtUpdate);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{uuid}/dt_update/{dt_update}/status/{status}")
    public ResponseEntity<?> update(@PathVariable UUID uuid,
                                    @PathVariable LocalDateTime dtUpdate,
                                    @PathVariable TaskStatus status) {
        this.update(uuid, dtUpdate, status);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
