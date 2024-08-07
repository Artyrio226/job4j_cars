package ru.job4j.cars.service.car;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    Optional<Car> create(Car car);
    boolean update(Car car);
    boolean delete(Long id);
    List<Car> findAllOrderById();
    Optional<Car> findById(Long id);
    List<Car> findByLikeName(String name);
    Optional<Car> findByName(String name);
}
