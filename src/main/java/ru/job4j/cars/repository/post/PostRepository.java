package ru.job4j.cars.repository.post;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> create(Post post);
    boolean update(Post post);
    boolean delete(int id);
    List<Post> findAllOrderById();
    Optional<Post> findById(int id);
    List<Post> findByLikeDescription(String key);
    List<Post> findPostsForLastDays(int day);
    List<Post> findPostsWithPhoto();
    List<Post> findCarsByName(String name);
}
