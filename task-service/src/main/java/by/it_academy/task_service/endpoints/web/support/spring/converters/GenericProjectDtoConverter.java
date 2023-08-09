package by.it_academy.task_service.endpoints.web.support.spring.converters;

import by.it_academy.task_service.core.dto.ProjectDto;
import by.it_academy.task_service.core.dto.UserRef;
import by.it_academy.task_service.dao.entity.Project;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenericProjectDtoConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> pairs = new HashSet<>();

        pairs.add(new ConvertiblePair(Project.class, ProjectDto.class));

        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() == ProjectDto.class) {
            return source;
        }

        ProjectDto projectDto = new ProjectDto();

        Project project = (Project) source;

        projectDto.setUuid(project.getUuid());
        projectDto.setDtCreate(project.getDtCreate());
        projectDto.setDtUpdate(project.getDtUpdate());
        projectDto.setName(project.getName());
        projectDto.setDescription(project.getDescription());
        projectDto.setManager(new UserRef(project.getManager()));

        List<UserRef> userRefs = project.getStaff().stream().map(UserRef::new).toList();

        projectDto.setStaff(userRefs);
        projectDto.setStatus(project.getStatus());

        return projectDto;
    }
}
