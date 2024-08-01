package ru.job4j.cars.repository.car;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    Optional<Car> create(Car car);
    boolean update(Car car);
    boolean delete(Long id);
    List<Car> findAllOrderById();
    Optional<Car> findById(Long id);
    List<Car> findByLikeName(String name);
    Optional<Car> findByName(String name);
}
