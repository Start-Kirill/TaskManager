package by.it_academy.task_service.core.enums;

public enum ProjectStatus {
    ACTIVE("ACTIVE"), ARCHIVED("ARCHIVED");

    private String name;

    ProjectStatus(String name) {
        this.name = name;
    }
}
