package by.it_academy.task_service.core.dto;

import by.it_academy.task_service.core.enums.ProjectStatus;

import java.util.List;

public class ProjectCreateDto {

    private String name;

    private String description;

    private UserRef manager;

    private List<UserRef> staff;

    private ProjectStatus status;

    public ProjectCreateDto() {
    }

    public ProjectCreateDto(String name, String description, UserRef manager, List<UserRef> staff, ProjectStatus status) {
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.staff = staff;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserRef getManager() {
        return manager;
    }

    public void setManager(UserRef manager) {
        this.manager = manager;
    }

    public List<UserRef> getStaff() {
        return staff;
    }

    public void setStaff(List<UserRef> staff) {
        this.staff = staff;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }
}
