package ru.job4j.cars.service.post;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.post.PostRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimplePostService implements PostService {
    private final PostRepository postRepository;
    private final String storageDirectory;

    public SimplePostService(PostRepository postRepository, @Value("${file.directory}") String storageDirectory) {
        this.postRepository = postRepository;
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

    @Override
    public Optional<Post> create(PostDto postDto, User user, List<FileDto> list) {
        List<Photo> photoList = createPhotoList(list);
        Post post = createPost(postDto, user, photoList);
        return postRepository.create(post);
    }

    @Override
    public boolean update(Post post) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Post> findAllOrderById() {
        return null;
    }

    @Override
    public Optional<Post> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Post> findByLikeDescription(String key) {
        return null;
    }

    @Override
    public List<Post> findPostsForLastDays(int day) {
        return null;
    }

    @Override
    public List<Post> findPostsWithPhoto() {
        return null;
    }

    @Override
    public List<Post> findCarsByName(String name) {
        return null;
    }

    private Post createPost(PostDto postDto, User user, List<Photo> list) {
        Engine engine = new Engine();
        engine.setName(postDto.getNameEngine());

        Owner owner = new Owner();
        owner.setUser(user);
        owner.setName(postDto.getNameOwner());

        Car car = new Car();
        car.setEngine(engine);
        car.setOwner(owner);
        car.setName(postDto.getName());
        car.setBody(postDto.getBody());
        car.setDrive(postDto.getDrive());
        car.setTransmission(postDto.getTransmission());
        car.setMileage(postDto.getMileage());
        car.setYear(postDto.getYear());

        Post post = new Post();
        post.setDescription(postDto.getDescription());
        post.setCar(car);
        post.setUser(user);
        post.setPhotos(list);

        return post;
    }

    private List<Photo> createPhotoList(List<FileDto> list) {
        List<Photo> photoList = new ArrayList<>();
        for (FileDto fileDto: list) {
            photoList.add(create(fileDto));
        }
        return photoList;
    }

    private Photo create(FileDto fileDto) {
        var path = getNewFilePath(fileDto.getName());
        writeFileBytes(path, fileDto.getContent());
        var photo = new Photo();
        photo.setName(fileDto.getName());
        photo.setPath(path);
        return photo;
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }
}
