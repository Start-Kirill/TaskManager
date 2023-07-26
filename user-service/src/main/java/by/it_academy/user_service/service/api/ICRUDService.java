package by.it_academy.user_service.service.api;

import by.it_academy.user_service.core.dto.CustomPage;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ICRUDService<K, T> {

    void save(T t);

    void update(T t, UUID uuid, LocalDateTime version);

    CustomPage<K> get(Integer page, Integer size);

    K get(UUID uuid);
}
