package ru.job4j.cars.repository.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Post> findPostsForLastDays(int days) {
        return crudRepository.query(
                "FROM Post WHERE created > :fDateMinusDays", Post.class,
                Map.of("fDateMinusDays", LocalDateTime.now().minusDays(days))
        );
    }

    @Override
    public List<Post> findPostsWithPhoto() {
        return crudRepository.query("FROM Post WHERE photos.size > 0", Post.class);
    }

    @Override
    public List<Post> findCarsByName(String name) {
        return crudRepository.query(
                "from Post where name like :fName", Post.class,
                Map.of("fName", "%" + name + "%")
        );
    }
}
