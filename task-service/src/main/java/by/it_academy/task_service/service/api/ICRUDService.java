package by.it_academy.task_service.service.api;

import by.it_academy.task_manager_common.dto.CustomPage;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ICRUDService<E, C> {

    E create(C c);

    E update(C c, UUID uuid, LocalDateTime dtUpdate);

    E get(UUID uuid);

}
