package by.it_academy.task_service.service.support.spring.converters;

import by.it_academy.task_service.core.dto.TaskCreateDto;
import by.it_academy.task_service.dao.entity.Task;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.HashSet;
import java.util.Set;

public class GenericTaskConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {

        Set<ConvertiblePair> pairs = new HashSet<>();

        pairs.add(new ConvertiblePair(TaskCreateDto.class, Task.class));

        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() == Task.class) {
            return source;
        }

        Task task = new Task();

        TaskCreateDto taskCreateDto = (TaskCreateDto) source;

        task.setDescription(taskCreateDto.getDescription());
        task.setStatus(taskCreateDto.getStatus());
        task.setImplementer(taskCreateDto.getImplementer().getUuid());
        task.setTitle(taskCreateDto.getTitle());

        return task;
    }
}
