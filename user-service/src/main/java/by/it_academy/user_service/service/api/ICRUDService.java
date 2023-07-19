package by.it_academy.user_service.service.api;

import java.util.List;

public interface ICRUDService<K, T> {

    K save(T t);

    K update(T t);

    List<K> get();

    K get(Long id);
}
