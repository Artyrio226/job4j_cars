package ru.job4j.cars.repository.engine;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

public interface EngineRepository {
    Optional<Engine> create(Engine engine);
    boolean update(Engine engine);
    boolean delete(int id);
    List<Engine> findAllOrderById();
    Optional<Engine> findById(int id);
    List<Engine> findByLikeName(String name);
    Optional<Engine> findByName(String name);
}
