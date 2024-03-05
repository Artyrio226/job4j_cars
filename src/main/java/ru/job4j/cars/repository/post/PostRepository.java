package ru.job4j.cars.repository.post;

import ru.job4j.cars.model.Post;

import java.util.List;

public interface PostRepository {
    List<Post> findPostsForLastDays(int day);
    List<Post> findPostsWithPhoto();
    List<Post> findCarsByName(String name);
}
