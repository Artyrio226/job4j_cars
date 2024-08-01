package ru.job4j.cars.repository.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Post> create(Post post) {
        return crudRepository.run(session -> session.persist(post)) ? Optional.of(post) : Optional.empty();
    }

    @Override
    public boolean update(Post post) {
        return crudRepository.run(session -> session.merge(post));
    }

    @Override
    public boolean delete(Long id) {
        return crudRepository.run(
                "delete from Post where id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public List<Post> findAllOrderById() {
        return crudRepository.query("from Post order by id asc", Post.class);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return crudRepository.optional(
                "from Post where id = :fId", Post.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Post> findByLikeDescription(String key) {
        return crudRepository.query(
                "from Post where description like :fKey", Post.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    @Override
    public List<Post> findPostsForLastDays(int days) {
        return crudRepository.query(
                "FROM Post WHERE created > :fDateMinusDays", Post.class,
                Map.of("fDateMinusDays", LocalDateTime.now().minusDays(days))
        );
    }

    @Override
    public List<Post> findPostsWithPhoto() {
        return crudRepository.query("FROM Post p join fetch p.photos WHERE p.photos is not empty", Post.class);
    }

    @Override
    public List<Post> findCarsByName(String name) {
        return crudRepository.query(
                "from Post p where p.car.name like :fName", Post.class,
                Map.of("fName", "%" + name + "%")
        );
    }
}
