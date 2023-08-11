package by.it_academy.task_service.service.support.spring.converters;

import by.it_academy.task_service.core.dto.ProjectCreateDto;
import by.it_academy.task_service.core.dto.UserRef;
import by.it_academy.task_service.dao.entity.Project;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GenericProjectConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> pairs = new HashSet<>();

        pairs.add(new ConvertiblePair(ProjectCreateDto.class, Project.class));

        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() == Project.class) {
            return source;
        }

        Project project = new Project();

        ProjectCreateDto projectCreateDto = (ProjectCreateDto) source;

        project.setName(projectCreateDto.getName());
        project.setDescription(projectCreateDto.getDescription());
        project.setManager(projectCreateDto.getManager().getUuid());
        project.setStaff(projectCreateDto.getStaff().stream().map(UserRef::getUuid).collect(Collectors.toSet()));
        project.setStatus(projectCreateDto.getStatus());

        return project;
    }
}
