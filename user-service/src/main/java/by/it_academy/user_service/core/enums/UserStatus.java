package by.it_academy.user_service.core.enums;

public enum UserStatus {
    WAITING_ACTIVATION("WAITING_ACTIVATION"), ACTIVATED("ACTIVATED"), DEACTIVATED("DEACTIVATED");

    private String name;

    UserStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
