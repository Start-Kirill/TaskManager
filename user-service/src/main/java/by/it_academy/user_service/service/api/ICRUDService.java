package by.it_academy.user_service.service.api;

import java.util.UUID;

public interface ICRUDService<K, T> {

    K save(T t);

    K update(T t, UUID uuid, Long version);

    K get(Integer page, Integer size);

    K get(UUID uuid);
}
