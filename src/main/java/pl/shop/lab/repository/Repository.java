package pl.shop.lab.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    List<T> loadAll();

    void saveAll(List<T> items);

    List<T> findAll();

    Optional<T> findById(String id);

    T insert(T t);

    void update(T t);
}