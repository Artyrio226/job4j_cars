package ru.job4j.cars.repository.car;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateCarRepository implements CarRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Car> create(Car car) {
        return crudRepository.run(session -> session.persist(car)) ? Optional.of(car) : Optional.empty();
    }

    @Override
    public boolean update(Car car) {
        return crudRepository.run(session -> session.merge(car));
    }

    @Override
    public boolean delete(Long id) {
        return crudRepository.run(
                "delete from Car where id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public List<Car> findAllOrderById() {
        return crudRepository.query("from Car order by id asc", Car.class);
    }

    @Override
    public Optional<Car> findById(Long id) {
        return crudRepository.optional(
                "from Car where id = :fId", Car.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Car> findByLikeName(String name) {
        return crudRepository.query(
                "from Car where name like :fName", Car.class,
                Map.of("fName", "%" + name + "%")
        );
    }

    @Override
    public Optional<Car> findByName(String name) {
        return crudRepository.optional(
                "from Car where name = :fName", Car.class,
                Map.of("fName", name)
        );
    }
}
