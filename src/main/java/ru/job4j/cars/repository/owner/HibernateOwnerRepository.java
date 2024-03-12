package ru.job4j.cars.repository.owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateOwnerRepository implements OwnerRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Owner> create(Owner owner) {
        return crudRepository.run(session -> session.persist(owner)) ? Optional.of(owner) : Optional.empty();
    }

    @Override
    public boolean update(Owner owner) {
        return crudRepository.run(session -> session.merge(owner));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.run(
                "delete from Owner where id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public List<Owner> findAllOrderById() {
        return crudRepository.query("from Owner order by id asc", Owner.class);
    }

    @Override
    public Optional<Owner> findById(int id) {
        return crudRepository.optional(
                "from Owner where id = :fId", Owner.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Owner> findByLikeName(String name) {
        return crudRepository.query(
                "from Owner where name like :fName", Owner.class,
                Map.of("fName", "%" + name + "%")
        );
    }

    @Override
    public Optional<Owner> findByName(String name) {
        return crudRepository.optional(
                "from Owner where name = :fName", Owner.class,
                Map.of("fName", name)
        );
    }
}
