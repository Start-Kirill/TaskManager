package by.it_academy.user_service.core.enums;

public enum UserRole {
    ADMIN("ADMIN"), USER("USER");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
