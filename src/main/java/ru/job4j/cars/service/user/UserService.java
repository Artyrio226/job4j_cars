package ru.job4j.cars.service.user;

import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> create(User user);
    boolean update(User user);
    boolean delete(int id);
    List<User> findAllOrderById();
    Optional<User> findById(int id);
    List<User> findByLikeLogin(String key);
    Optional<User> findByLogin(String login);
}
