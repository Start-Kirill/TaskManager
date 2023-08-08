package by.it_academy.task_service.core.dto;

import java.util.UUID;

public class UserRef {

    private UUID uuid;

    public UserRef() {
    }

    public UserRef(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
