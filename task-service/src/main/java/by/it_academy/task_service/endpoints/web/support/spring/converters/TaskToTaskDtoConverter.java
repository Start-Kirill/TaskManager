package by.it_academy.task_service.endpoints.web.support.spring.converters;

import by.it_academy.task_service.core.dto.ProjectRef;
import by.it_academy.task_service.core.dto.TaskDto;
import by.it_academy.task_service.core.dto.UserRef;
import by.it_academy.task_service.dao.entity.Task;
import org.springframework.core.convert.converter.Converter;

public class TaskToTaskDtoConverter implements Converter<Task, TaskDto> {

    @Override
    public TaskDto convert(Task source) {
        TaskDto taskDto = new TaskDto();


        taskDto.setUuid(source.getUuid());
        taskDto.setDtCreate(source.getDtCreate());
        taskDto.setDtUpdate(source.getDtUpdate());
        taskDto.setTitle(source.getTitle());
        taskDto.setDescription(source.getDescription());
        taskDto.setProject(new ProjectRef(source.getProject().getUuid()));
        taskDto.setImplementer(new UserRef(source.getImplementer()));
        taskDto.setStatus(source.getStatus());


        return taskDto;
    }
}
