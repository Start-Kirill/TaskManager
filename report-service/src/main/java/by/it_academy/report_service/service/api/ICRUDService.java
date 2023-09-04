package by.it_academy.report_service.service.api;

import by.it_academy.task_manager_common.dto.CustomPage;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ICRUDService<C, D> {

    D save(C c);

    D update(C c, UUID uuid, LocalDateTime dtUpdate);

    D get(UUID uuid);

    CustomPage<D> get(Integer page, Integer size);
}
