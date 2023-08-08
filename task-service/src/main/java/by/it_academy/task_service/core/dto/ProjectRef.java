package by.it_academy.task_service.core.dto;

import java.util.UUID;

public class ProjectRef {

    private UUID uuid;

    public ProjectRef() {
    }

    public ProjectRef(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
