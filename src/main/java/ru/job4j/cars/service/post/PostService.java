package ru.job4j.cars.service.post;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<Post> create(PostDto postDto, User user, List<FileDto> list);
    boolean update(Post post);
    boolean delete(int id);
    List<Post> findAllOrderById();
    Optional<Post> findById(int id);
    List<Post> findByLikeDescription(String key);
    List<Post> findPostsForLastDays(int day);
    List<Post> findPostsWithPhoto();
    List<Post> findCarsByName(String name);
}
