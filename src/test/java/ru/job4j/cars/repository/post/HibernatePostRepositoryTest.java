package ru.job4j.cars.repository.post;

import org.junit.jupiter.api.*;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.car.HibernateCarRepository;
import ru.job4j.cars.repository.engine.HibernateEngineRepository;
import ru.job4j.cars.repository.owner.HibernateOwnerRepository;
import ru.job4j.cars.repository.photo.HibernatePhotoRepository;
import ru.job4j.cars.repository.user.HibernateUserRepository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

class HibernatePostRepositoryTest {
    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    static CrudRepository cr = new CrudRepository(sf);

    static HibernateUserRepository userRepository = new HibernateUserRepository(cr);
    static HibernatePostRepository postRepository = new HibernatePostRepository(cr);
    static HibernateOwnerRepository ownerRepository = new HibernateOwnerRepository(cr);
    static HibernateCarRepository carRepository = new HibernateCarRepository(cr);
    static HibernatePhotoRepository photoRepository = new HibernatePhotoRepository(cr);
    static HibernateEngineRepository engineRepository = new HibernateEngineRepository(cr);

    private User user2;
    private Car car2;
    private Post post;

    @BeforeEach
    public void init() {
        User user = new User();
        user.setLogin("admin1");
        user.setPassword("admin1");

        Engine engine = new Engine();
        engine.setName("Turbo V8");

        Owner owner = new Owner();
        owner.setName("Peter");
        owner.setUser(user);

        Car car = new Car();
        car.setName("Lada");
        car.setOwner(owner);
        car.setEngine(engine);

        user2 = new User();
        user2.setLogin("admin2");
        user2.setPassword("admin");

        Engine engine2 = new Engine();
        engine2.setName("Hybrid");

        Owner owner2 = new Owner();
        owner2.setName("Roman");
        owner2.setUser(user2);

        car2 = new Car();
        car2.setName("Kamaz");
        car2.setOwner(owner2);
        car2.setEngine(engine2);

        Photo photo = new Photo();
        photo.setName("photo1");
        photo.setPath("files/photo1");

        post = new Post();
        post.setUser(user);
        post.setCar(car);
        post.setDescription("Description1");
        post.getPhotos().add(photo);
        postRepository.create(post);
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

        var owners = ownerRepository.findAllOrderById();
        for (var owner : owners) {
            ownerRepository.delete(owner.getId());
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
        StandardServiceRegistryBuilder.destroy(registry);
    }

    /**
     * Удачное сохранение объявления в БД.
     */
    @Test
    public void whenCreatePostThenOptionalIsNotEmpty() throws Exception {
        Post post2 = new Post();
        post2.setUser(user2);
        post2.setCar(car2);
        post2.setDescription("Description2");

        var result = postRepository.create(post2);
        var post2Result = postRepository.findById(post2.getId());

        assertThat(result).isNotEmpty();
        assertThat(post2Result.get().getDescription()).isEqualTo(post2.getDescription());
    }

    /**
     * Неудачное сохранение объявления в БД.
     */
    @Test
    public void whenCreatePostThenOptionalIsEmpty() throws Exception {
        Post post2 = new Post();
        post2.setUser(user2);
        post2.setCar(car2);

        var result = postRepository.create(post2);
        var post2Result = postRepository.findById(post2.getId());

        assertThat(result).isEmpty();
        assertThat(post2Result).isEmpty();
    }

    /**
     * Объявление найдено в БД по id.
     */
    @Test
    public void whenFindPostById() throws Exception {
        var result = postRepository.findById(post.getId());

        assertThat(result).isNotEmpty();
        assertThat(result.get().getDescription()).isEqualTo(post.getDescription());
    }

    /**
     * Объявление не найдено в БД по id.
     */
    @Test
    public void whenNotFoundPostById() throws Exception {
        var result = postRepository.findById(post.getId() + 1);

        assertThat(result).isEmpty();
    }

    /**
     * Удачное обновление объявления в БД.
     */
    @Test
    public void whenUpdatePostThenTrue() throws Exception {
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
        var result = postRepository.delete(post.getId() + 1);
        var postResult = postRepository.findById(post.getId());

        assertThat(result).isFalse();
        assertThat(postResult).isNotEmpty();
        assertThat(postResult.get().getDescription()).isEqualTo(post.getDescription());
    }

    /**
     * Найдены все объявления в БД.
     */
    @Test
    public void whenFindAllPostsOrderedById() throws Exception {
        Post post2 = new Post();
        post2.setUser(user2);
        post2.setCar(car2);
        post2.setDescription("Description2");
        postRepository.create(post2);

        var result = postRepository.findAllOrderById();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(post, post2);
    }

    /**
     * Найдены все объявления в БД, содержащие в имени "cri".
     */
    @Test
    public void whenFindAllPostsByLikeDescriptionCri() throws Exception {
        Post post2 = new Post();
        post2.setUser(user2);
        post2.setCar(car2);
        post2.setDescription("Des ription2");
        postRepository.create(post2);

        var result = postRepository.findByLikeDescription("cri");

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(post);
    }

    /**
     * Не найдены объявления в БД, содержащие в имени "moon".
     */
    @Test
    public void whenNotFoundPostsByLikeDescriptionMoon() throws Exception {
        var result = postRepository.findByLikeDescription("moon");

        assertThat(result).isEmpty();
    }

    /**
     * Найдены все объявления в БД, содержащие в имени авто "Lada".
     */
    @Test
    public void whenFindPostsWithCarByName() throws Exception {
        var result = postRepository.findCarsByName("Lada");

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getId()).isEqualTo(post.getId());

    }

    /**
     * Не найдены объявления в БД, содержащие в имени авто "Kamaz".
     */
    @Test
    public void whenNotFoundPostsWithCarByName() throws Exception {
        var result = postRepository.findCarsByName("Kamaz");

        assertThat(result).isEmpty();

    }

    /**
     * Найдены все объявления в БД за последний день.
     */
    @Test
    public void whenFindPostsForLastDay() throws Exception {
        var result = postRepository.findPostsForLastDays(1);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getId()).isEqualTo(post.getId());
    }

    /**
     * Не найдены объявления в БД за последний день.
     */
    @Test
    public void whenNotFoundPostsForLastDay() throws Exception {
        post.setCreated(LocalDateTime.now().minusDays(2));
        postRepository.update(post);

        var result = postRepository.findPostsForLastDays(1);

        assertThat(result).isEmpty();
    }

    /**
     * Найдены все объявления в БД с фото.
     */
    @Test
    public void whenFindPostsWithPhoto() throws Exception {
        Post post2 = new Post();
        post2.setUser(user2);
        post2.setCar(car2);
        post2.setDescription("Description2");
        postRepository.create(post2);

        var result = postRepository.findPostsWithPhoto();
        var resultAll = postRepository.findAllOrderById();

        assertThat(resultAll).hasSize(2);
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(post);
    }
}