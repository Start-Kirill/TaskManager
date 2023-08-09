package by.it_academy.task_service.core.dto;

import by.it_academy.task_service.core.enums.TaskStatus;

public class TaskCreateDto {

    private ProjectRef project;

    private String title;

    private String description;

    private TaskStatus status;

    private UserRef implementer;

    public TaskCreateDto() {
    }

    public TaskCreateDto(ProjectRef project, String title, String description, TaskStatus status, UserRef implementer) {
        this.project = project;
        this.title = title;
        this.description = description;
        this.status = status;
        this.implementer = implementer;
    }

    public ProjectRef getProject() {
        return project;
    }

    public void setProject(ProjectRef project) {
        this.project = project;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public UserRef getImplementer() {
        return implementer;
    }

    public void setImplementer(UserRef implementer) {
        this.implementer = implementer;
    }
}
