package by.it_academy.task_service.endpoints.web.support.spring.converters;

import by.it_academy.task_service.core.dto.ProjectRef;
import by.it_academy.task_service.core.dto.TaskDto;
import by.it_academy.task_service.core.dto.UserRef;
import by.it_academy.task_service.dao.entity.Task;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.HashSet;
import java.util.Set;

public class GenericTaskDtoConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> pairs = new HashSet<>();

        pairs.add(new ConvertiblePair(Task.class, TaskDto.class));

        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() == TaskDto.class) {
            return source;
        }

        TaskDto taskDto = new TaskDto();

        Task task = (Task) source;

        taskDto.setUuid(task.getUuid());
        taskDto.setDtCreate(task.getDtCreate());
        taskDto.setDtUpdate(task.getDtUpdate());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setProject(new ProjectRef(task.getProject().getUuid()));
        taskDto.setImplementer(new UserRef(task.getImplementer()));
        taskDto.setStatus(task.getStatus());


        return taskDto;
    }
}
