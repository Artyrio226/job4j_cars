package ru.job4j.cars.service.car;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.car.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCarService implements CarService {
    private CarRepository carRepository;

    @Override
    public Optional<Car> create(Car car) {
        return carRepository.create(car);
    }

    @Override
    public boolean update(Car car) {
        return carRepository.update(car);
    }

    @Override
    public boolean delete(int id) {
        return carRepository.delete(id);
    }

    @Override
    public List<Car> findAllOrderById() {
        return carRepository.findAllOrderById();
    }

    @Override
    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }

    @Override
    public List<Car> findByLikeName(String name) {
        return carRepository.findByLikeName(name);
    }

    @Override
    public Optional<Car> findByName(String name) {
        return carRepository.findByName(name);
    }
}
