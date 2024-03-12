package ru.job4j.cars.repository.car;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.engine.HibernateEngineRepository;
import ru.job4j.cars.repository.owner.HibernateOwnerRepository;
import ru.job4j.cars.repository.user.HibernateUserRepository;

import static org.assertj.core.api.Assertions.*;

class HibernateCarRepositoryTest {
    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    static CrudRepository cr = new CrudRepository(sf);
    static HibernateUserRepository userRepository = new HibernateUserRepository(cr);
    static HibernateCarRepository carRepository = new HibernateCarRepository(cr);
    static HibernateOwnerRepository ownerRepository = new HibernateOwnerRepository(cr);
    static HibernateEngineRepository engineRepository = new HibernateEngineRepository(cr);

    private Owner owner;
    private Owner owner2;
    private Engine engine;
    private Engine engine2;
    private Car car;

    @BeforeEach
    public void init() {
        User user = new User();
        user.setLogin("admin1");
        user.setPassword("admin1");

        owner = new Owner();
        owner.setName("Peter");
        owner.setUser(user);

        engine = new Engine();
        engine.setName("Turbo V8");

        User user2 = new User();
        user2.setLogin("admin2");
        user2.setPassword("admin");

        engine2 = new Engine();
        engine2.setName("Hybrid");

        owner2 = new Owner();
        owner2.setName("Roman");
        owner2.setUser(user2);

        car = new Car();
        car.setName("Lada");
        car.setOwner(owner);
        car.setEngine(engine);
        carRepository.create(car);
    }

    @AfterEach
    public void clean() {
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
     * Удачное сохранение авто в БД.
     */
    @Test
    public void whenCreateCarThenOptionalIsNotEmpty() throws Exception {
        Car car2 = new Car();
        car2.setName("Kamaz");
        car2.setOwner(owner2);
        car2.setEngine(engine2);

        var result = carRepository.create(car2);
        var carResult = carRepository.findById(car2.getId());

        assertThat(result).isNotEmpty();
        assertThat(carResult.get().getName()).isEqualTo(car2.getName());
    }

    /**
     * Неудачное сохранение авто в БД.
     */
    @Test
    public void whenCreateCarThenOptionalIsEmpty() throws Exception {
        Car car2 = new Car();
        car2.setName("Kamaz");
        car2.setOwner(owner);
        car2.setEngine(engine);

        var result = carRepository.create(car2);
        var car2Result = carRepository.findById(car2.getId());

        assertThat(result).isEmpty();
        assertThat(car2Result).isEmpty();
    }

    /**
     * Авто найдено в БД по id.
     */
    @Test
    public void whenFindCarById() throws Exception {
        var result = carRepository.findById(car.getId());

        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo(car.getName());
    }

    /**
     * Авто не найдено в БД по id.
     */
    @Test
    public void whenNotFoundCarById() throws Exception {
        var result = carRepository.findById(car.getId() + 1);

        assertThat(result).isEmpty();
    }

    /**
     * Удачное обновление авто в БД.
     */
    @Test
    public void whenUpdateCarThenTrue() throws Exception {
        String newName = "Porsche";
        car.setName(newName);

        var result = carRepository.update(car);
        var carResult = carRepository.findById(car.getId());

        assertThat(result).isTrue();
        assertThat(carResult.get().getName()).isEqualTo(newName);
    }

    /**
     * Неудачное обновление авто в БД.
     */
    @Test
    public void whenUpdateCarThenFalse() throws Exception {
        Car car2 = new Car();
        car2.setName("Kamaz");
        car2.setOwner(owner2);
        car2.setEngine(engine2);
        carRepository.create(car2);

        car2.setEngine(engine);

        var result = carRepository.update(car2);
        var car2Result = carRepository.findById(car2.getId());

        assertThat(result).isFalse();
        assertThat(car2Result.get().getEngine().getName()).isEqualTo(engine2.getName());
    }

    /**
     * Удачное удаление авто из БД по id.
     */
    @Test
    public void whenDeleteCarThenTrue() throws Exception {
        var result = carRepository.delete(car.getId());
        var carResult = carRepository.findById(car.getId());

        assertThat(result).isTrue();
        assertThat(carResult).isEmpty();
    }

    /**
     * Неудачное удаление авто из БД по id.
     */
    @Test
    public void whenDeleteCarThenFalse() throws Exception {
        var result = carRepository.delete(car.getId() + 1);
        var carResult = carRepository.findById(car.getId());

        assertThat(result).isFalse();
        assertThat(carResult).isNotEmpty();
        assertThat(carResult.get().getName()).isEqualTo(car.getName());
    }

    /**
     * Найдены все авто в БД.
     */
    @Test
    public void whenFindAllCarsOrderedById() throws Exception {
        Car car2 = new Car();
        car2.setName("Kamaz");
        car2.setOwner(owner2);
        car2.setEngine(engine2);
        carRepository.create(car2);

        var result = carRepository.findAllOrderById();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(car, car2);
    }

    /**
     * Найдены все авто в БД, содержащие в имени "ada".
     */
    @Test
    public void whenFindAllCarsByLikeNameAda() throws Exception {
        Car car2 = new Car();
        car2.setName("Kamaz");
        car2.setOwner(owner2);
        car2.setEngine(engine2);
        carRepository.create(car2);

        var result = carRepository.findByLikeName("ada");

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(car);
    }

    /**
     * Не найдены авто в БД, содержащие в имени "man".
     */
    @Test
    public void whenNotFoundCarsByLikeNameMan() throws Exception {
        var result = carRepository.findByLikeName("man");

        assertThat(result).isEmpty();
    }

    /**
     * Найден авто в БД по имени.
     */
    @Test
    public void whenFindCarByNamePeter() throws Exception {
        var result = carRepository.findByName("Lada");

        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo(car.getName());
    }

    /**
     * Не найден авто в БД по имени.
     */
    @Test
    public void whenNotFoundCarByNameIvan() throws Exception {
        var result = carRepository.findByName("Ivan");

        assertThat(result).isEmpty();
    }
}