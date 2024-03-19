package ru.job4j.cars.service.photo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.Photo;
import ru.job4j.cars.repository.photo.PhotoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimplePhotoService implements PhotoService {
    private final PhotoRepository photoRepository;
    private final String storageDirectory;

    public SimplePhotoService(PhotoRepository hibernateFileRepository,
                             @Value("${file.directory}") String storageDirectory) {
        this.photoRepository = hibernateFileRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Photo> create(FileDto fileDto) {
        var path = getNewFilePath(fileDto.getName());
        writeFileBytes(path, fileDto.getContent());
        var photo = new Photo();
        photo.setName(fileDto.getName());
        photo.setPath(path);
        return photoRepository.create(photo);
    }

    @Override
    public boolean update(Photo photo) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        var fileOptional = photoRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return false;
        }
        deleteFile(fileOptional.get().getPath());
        return photoRepository.delete(id);
    }

    @Override
    public List<FileDto> findAllOrderById() {
        return null;
    }

    @Override
    public Optional<FileDto> findById(int id) {
        var fileOptional = photoRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new FileDto(fileOptional.get().getName(), content));
    }

    @Override
    public List<FileDto> findByLikeName(String name) {
        return null;
    }

    @Override
    public Optional<FileDto> findByName(String name) {
        return Optional.empty();
    }
}
