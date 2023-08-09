package by.it_academy.task_manager_common.enums;

public enum UserRole {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER"), MANAGER("ROLE_MANAGER"), SYSTEM("ROLE_SYSTEM");

    private String roleName;

    UserRole(String name) {
        this.roleName = name;
    }

    public String getRoleName() {
        return roleName;
    }

}
