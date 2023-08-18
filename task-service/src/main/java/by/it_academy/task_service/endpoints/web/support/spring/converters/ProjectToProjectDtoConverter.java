package by.it_academy.task_service.endpoints.web.support.spring.converters;

import by.it_academy.task_service.core.dto.ProjectDto;
import by.it_academy.task_service.core.dto.UserRef;
import by.it_academy.task_service.dao.entity.Project;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class ProjectToProjectDtoConverter implements Converter<Project, ProjectDto> {
    @Override
    public ProjectDto convert(Project source) {
        ProjectDto projectDto = new ProjectDto();

        projectDto.setUuid(source.getUuid());
        projectDto.setDtCreate(source.getDtCreate());
        projectDto.setDtUpdate(source.getDtUpdate());
        projectDto.setName(source.getName());
        projectDto.setDescription(source.getDescription());
        projectDto.setManager(new UserRef(source.getManager()));

        List<UserRef> userRefs = source.getStaff().stream().map(UserRef::new).toList();

        projectDto.setStaff(userRefs);
        projectDto.setStatus(source.getStatus());

        return projectDto;
    }
}
