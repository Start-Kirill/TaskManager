package by.it_academy.task_service.service.support.spring.converters;

import by.it_academy.task_service.core.dto.ProjectCreateDto;
import by.it_academy.task_service.core.dto.UserRef;
import by.it_academy.task_service.dao.entity.Project;
import org.springframework.core.convert.converter.Converter;

import java.util.stream.Collectors;

public class ProjectCreateDtoToProjectConverter implements Converter<ProjectCreateDto, Project> {

    @Override
    public Project convert(ProjectCreateDto source) {
        Project project = new Project();

        project.setName(source.getName());
        project.setDescription(source.getDescription());
        project.setManager(source.getManager().getUuid());
        project.setStaff(source.getStaff().stream().map(UserRef::getUuid).collect(Collectors.toSet()));
        project.setStatus(source.getStatus());

        return project;
    }

}
