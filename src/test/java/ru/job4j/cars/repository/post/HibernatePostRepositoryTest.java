package ru.job4j.cars.repository.post;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.HibernateUtil;
import ru.job4j.cars.repository.car.HibernateCarRepository;
import ru.job4j.cars.repository.engine.HibernateEngineRepository;
import ru.job4j.cars.repository.photo.HibernatePhotoRepository;
import ru.job4j.cars.repository.user.HibernateUserRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HibernatePostRepositoryTest {

    @Autowired
    HibernateUserRepository userRepository;
    @Autowired
    HibernatePostRepository postRepository;
    @Autowired
    HibernateCarRepository carRepository;
    @Autowired
    HibernatePhotoRepository photoRepository;
    @Autowired
    HibernateEngineRepository engineRepository;

    private User user2;
    private User user;
    private Car car2;
    private Post post;
    private Post post2;

    @BeforeEach
    public void init() {
        user = User.builder()
                .username("Test1")
                .email("test@mail.ru")
                .password("test")
                .phoneNumber("+79051111111")
                .build();

        Engine engine = Engine.builder()
                .capacity(3.0)
                .hp(300)
                .fuel("petrol")
                .build();

        Car car = Car.builder()
                .name("Lada")
                .body(Body.SEDAN)
                .color("red")
                .transmission(Transmission.MANUAL)
                .years(2020)
                .mileage(20000)
                .engine(engine)
                .build();

        user2 = User.builder()
                .username("Test2")
                .email("test2@mail.ru")
                .password("test2")
                .phoneNumber("+79051111112")
                .build();

        Engine engine2 = Engine.builder()
                .capacity(5.0)
                .hp(1000)
                .fuel("diesel")
                .build();

        car2 = Car.builder()
                .name("Kamaz")
                .body(Body.TRUCK)
                .color("green")
                .transmission(Transmission.MANUAL)
                .years(2021)
                .mileage(50000)
                .engine(engine2)
                .build();

        Photo photo = Photo.builder()
                .name("photo1")
                .path("files/photo1")
                .build();

        post = Post.builder()
                .description("description")
                .price(1500000)
                .city("Moscow")
                .created(LocalDateTime.now())
                .user(user)
                .car(car)
                .build();

        post2 = Post.builder()
                .description("description2")
                .price(700000)
                .city("Rome")
                .created(LocalDateTime.now())
                .user(user2)
                .car(car2)
                .build();

        post.addPhoto(photo);
        post.setCar(car);
        post2.setCar(car2);


        user.addPost(post);
        user.addPost(post2);
    }

    @AfterEach
    public void clean() {
        var photos = photoRepository.findAllOrderById();
        for (var photo : photos) {
            photoRepository.delete(photo.getId());
        }

        var posts = postRepository.findAllOrderById();
        for (var post : posts) {
            postRepository.delete(post.getId());
        }

        var cars = carRepository.findAllOrderById();
        for (var car : cars) {
            carRepository.delete(car.getId());
        }

        var engines = engineRepository.findAllOrderById();
        for (var engine : engines) {
            engineRepository.delete(engine.getId());
        }

        var users = userRepository.findAllOrderById();
        for (var user : users) {
            userRepository.delete(user.getId());
        }
    }

    @AfterAll
    public static void close() {
        StandardServiceRegistryBuilder.destroy(HibernateUtil.getServiceRegistry());
    }

    /**
     * Объявление найдено в БД по id.
     */
    @Test
    public void whenFindPostById() throws Exception {
        userRepository.save(user);
        var result = postRepository.findById(post.getId());

        assertThat(result).isNotEmpty();
        assertThat(result.get().getDescription()).isEqualTo(post.getDescription());
    }

    /**
     * Объявление не найдено в БД по id.
     */
    @Test
    public void whenNotFoundPostById() throws Exception {
        userRepository.save(user);
        var result = postRepository.findById(post2.getId() + 1);

        assertThat(result.isPresent()).isFalse();
    }

    /**
     * Удачное обновление объявления в БД.
     */
    @Test
    public void whenUpdatePostThenTrue() throws Exception {
        userRepository.save(user);
        String newDescription = "New Description";
        post.setDescription(newDescription);

        var result = postRepository.update(post);
        var postResult = postRepository.findById(post.getId());

        assertThat(result).isTrue();
        assertThat(postResult.get().getDescription()).isEqualTo(newDescription);
    }

    /**
     * Неудачное обновление объявления в БД.
     */
    @Test
    public void whenUpdatePostThenFalse() throws Exception {
        userRepository.save(user);
        String oldDescription = post.getDescription();
        post.setDescription(null);

        var result = postRepository.update(post);
        var postResult = postRepository.findById(post.getId());

        assertThat(result).isFalse();
        assertThat(postResult.get().getDescription()).isEqualTo(oldDescription);
    }

    /**
     * Удачное удаление объявления из БД по id.
     */
    @Test
    public void whenDeletePostThenTrue() throws Exception {
        userRepository.save(user);
        var result = postRepository.delete(post.getId());
        var postResult = postRepository.findById(post.getId());

        assertThat(result).isTrue();
        assertThat(postResult).isEmpty();
    }

    /**
     * Неудачное удаление объявления из БД по id.
     */
    @Test
    public void whenDeletePostThenFalse() throws Exception {
        userRepository.save(user);
        var result = postRepository.delete(post2.getId() + 1);
        var postResult = postRepository.findById(post2.getId());

        assertThat(result).isFalse();
        assertThat(postResult).isNotEmpty();
        assertThat(postResult.get().getDescription()).isEqualTo(post2.getDescription());
    }

    /**
     * Найдены все объявления в БД.
     */
    @Test
    public void whenFindAllPostsOrderedById() throws Exception {
        userRepository.save(user);
        var result = postRepository.findAllOrderById();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
    }

    /**
     * Найдены все объявления в БД, содержащие в описании объявления "cri".
     */
    @Test
    public void whenFindAllPostsByLikeDescriptionCri() throws Exception {
        userRepository.save(user);
        var result = postRepository.findByLikeDescription("cri");

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
    }

    /**
     * Не найдены объявления в БД, содержащие в имени "moon".
     */
    @Test
    public void whenNotFoundPostsByLikeDescriptionMoon() throws Exception {
        userRepository.save(user);
        var result = postRepository.findByLikeDescription("moon");

        assertThat(result).isEmpty();
    }

    /**
     * Найдены все объявления в БД, содержащие в имени авто "Lada".
     */
    @Test
    public void whenFindPostsWithCarByName() throws Exception {
        userRepository.save(user);
        var result = postRepository.findCarsByName("Lada");

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getId()).isEqualTo(post.getId());

    }

    /**
     * Не найдены объявления в БД, содержащие в имени авто "Kamaz".
     */
    @Test
    public void whenNotFoundPostsWithCarByName() throws Exception {
        userRepository.save(user);
        var result = postRepository.findCarsByName("ZIL");

        assertThat(result.size()).isEqualTo(0);

    }

    /**
     * Найдены все объявления в БД за последний день.
     */
    @Test
    public void whenFindPostsForLastDay() throws Exception {
        userRepository.save(user);
        var result = postRepository.findPostsForLastDays(1);

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(post.getId());
    }

    /**
     * Не найдены объявления в БД за последний день.
     */
    @Test
    public void whenNotFoundPostsForLastDay() throws Exception {
        userRepository.save(user);

        var result = postRepository.findPostsForLastDays(1);
        assertThat(result.size()).isEqualTo(2);

        post.setCreated(LocalDateTime.now().minusDays(2));
        postRepository.update(post);

        var result2 = postRepository.findPostsForLastDays(1);

        assertThat(result2.size()).isEqualTo(1);
    }

    /**
     * Найдены все объявления в БД с фото.
     */
    @Test
    public void whenFindPostsWithPhoto() throws Exception {
        userRepository.save(user);
        var result = postRepository.findPostsWithPhoto();
        var resultAll = postRepository.findAllOrderById();

        assertThat(resultAll).hasSize(2);
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
    }
}