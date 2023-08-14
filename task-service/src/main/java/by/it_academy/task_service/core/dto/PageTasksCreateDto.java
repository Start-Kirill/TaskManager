package by.it_academy.task_service.core.dto;

import by.it_academy.task_service.core.enums.TaskStatus;

import java.util.List;
import java.util.UUID;

public class PageTasksCreateDto {

    private Integer page;
    private Integer size;
    private List<UUID> projects;
    private List<UUID> implementers;
    private List<TaskStatus> statuses;

    public PageTasksCreateDto() {
    }

    public PageTasksCreateDto(Integer page, Integer size, List<UUID> project, List<UUID> implementers, List<TaskStatus> statuses) {
        this.page = page;
        this.size = size;
        this.projects = project;
        this.implementers = implementers;
        this.statuses = statuses;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<UUID> getProjects() {
        return projects;
    }

    public void setProjects(List<UUID> projects) {
        this.projects = projects;
    }

    public List<UUID> getImplementers() {
        return implementers;
    }

    public void setImplementers(List<UUID> implementers) {
        this.implementers = implementers;
    }

    public List<TaskStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<TaskStatus> statuses) {
        this.statuses = statuses;
    }
}
