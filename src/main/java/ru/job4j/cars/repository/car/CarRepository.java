package ru.job4j.cars.repository.car;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.cars.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
