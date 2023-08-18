package by.it_academy.task_service.service.support.spring.converters;

import by.it_academy.task_service.core.dto.TaskCreateDto;
import by.it_academy.task_service.dao.entity.Task;
import org.springframework.core.convert.converter.Converter;

public class TaskCreateDtoToTaskConverter implements Converter<TaskCreateDto, Task> {
    @Override
    public Task convert(TaskCreateDto source) {
        Task task = new Task();


        task.setDescription(source.getDescription());
        task.setStatus(source.getStatus());
        task.setImplementer(source.getImplementer().getUuid());
        task.setTitle(source.getTitle());

        return task;
    }
}
