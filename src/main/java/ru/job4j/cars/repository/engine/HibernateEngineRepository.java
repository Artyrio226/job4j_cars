package ru.job4j.cars.repository.engine;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
@AllArgsConstructor
public class HibernateEngineRepository implements EngineRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Engine> create(Engine engine) {
        return crudRepository.run(session -> session.persist(engine)) ? Optional.of(engine) : Optional.empty();
    }

    @Override
    public boolean update(Engine engine) {
        return crudRepository.run(session -> session.merge(engine));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.run(
                "delete from Engine where id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public List<Engine> findAllOrderById() {
        return crudRepository.query("from Engine order by id asc", Engine.class);
    }

    @Override
    public Optional<Engine> findById(int id) {
        return crudRepository.optional(
                "from Engine where id = :fId", Engine.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Engine> findByLikeName(String name) {
        return crudRepository.query(
                "from Engine where name like :fName", Engine.class,
                Map.of("fName", "%" + name + "%")
        );
    }

    @Override
    public Optional<Engine> findByName(String name) {
        return crudRepository.optional(
                "from Engine where name = :fName", Engine.class,
                Map.of("fName", name)
        );
    }
}
