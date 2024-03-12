package ru.job4j.cars.repository.photo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Photo;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePhotoRepository implements PhotoRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Photo> create(Photo photo) {
        return crudRepository.run(session -> session.persist(photo)) ? Optional.of(photo) : Optional.empty();
    }

    @Override
    public boolean update(Photo photo) {
        return crudRepository.run(session -> session.merge(photo));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.run(
                "delete from Photo where id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public List<Photo> findAllOrderById() {
        return crudRepository.query("from Photo order by id asc", Photo.class);
    }

    @Override
    public Optional<Photo> findById(int id) {
        return crudRepository.optional(
                "from Photo where id = :fId", Photo.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Photo> findByLikeName(String name) {
        return crudRepository.query(
                "from Photo where name like :fName", Photo.class,
                Map.of("fName", "%" + name + "%")
        );
    }

    @Override
    public Optional<Photo> findByName(String name) {
        return crudRepository.optional(
                "from Photo where name = :fName", Photo.class,
                Map.of("fName", name)
        );
    }
}
