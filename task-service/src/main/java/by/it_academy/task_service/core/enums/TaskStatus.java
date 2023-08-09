package by.it_academy.task_service.core.enums;

public enum TaskStatus {

    WAIT("WAIT"), BLOCK("BLOCK"), IN_WORK("IN_WORK"), DONE("DONE"), CLOSE("CLOSE");

    private String name;

    TaskStatus(String name) {
        this.name = name;
    }

}
