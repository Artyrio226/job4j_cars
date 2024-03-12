package ru.job4j.cars.repository.photo;

import ru.job4j.cars.model.Photo;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository {
    Optional<Photo> create(Photo photo);
    boolean update(Photo photo);
    boolean delete(int id);
    List<Photo> findAllOrderById();
    Optional<Photo> findById(int id);
    List<Photo> findByLikeName(String name);
    Optional<Photo> findByName(String name);
}
