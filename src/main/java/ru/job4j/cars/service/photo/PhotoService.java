package ru.job4j.cars.service.photo;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.Photo;

import java.util.List;
import java.util.Optional;

public interface PhotoService {
    Optional<Photo> create(FileDto fileDto);
    boolean update(Photo photo);
    boolean delete(int id);
    List<FileDto> findAllOrderById();
    Optional<FileDto> findById(int id);
    List<FileDto> findByLikeName(String name);
    Optional<FileDto> findByName(String name);
}
