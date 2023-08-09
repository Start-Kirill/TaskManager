package by.it_academy.task_manager_common.enums;

public enum UserRole {
    ADMIN("ADMIN"), USER("USER"), MANAGER("MANAGER"), SYSTEM("SYSTEM");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
