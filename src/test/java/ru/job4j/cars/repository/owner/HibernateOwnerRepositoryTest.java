package ru.job4j.cars.repository.owner;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.user.HibernateUserRepository;

import static org.assertj.core.api.Assertions.*;

class HibernateOwnerRepositoryTest {
    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    static CrudRepository cr = new CrudRepository(sf);
    static HibernateUserRepository userRepository = new HibernateUserRepository(cr);
    static HibernateOwnerRepository ownerRepository = new HibernateOwnerRepository(cr);

    private User user;
    private Owner owner;

    @BeforeEach
    public void init() {
        user = new User();
        user.setLogin("admin1");
        user.setPassword("admin1");

        owner = new Owner();
        owner.setName("Peter");
        owner.setUser(user);
        ownerRepository.create(owner);
    }

    @AfterEach
    public void clean() {
        var owners = ownerRepository.findAllOrderById();
        for (var owner : owners) {
            ownerRepository.delete(owner.getId());
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
     * Удачное сохранение владельца в БД.
     */
    @Test
    public void whenCreateOwnerThenOptionalIsNotEmpty() throws Exception {
        User user2 = new User();
        user2.setLogin("admin2");
        user2.setPassword("admin2");

        Owner owner2 = new Owner();
        owner2.setName("Ivan");
        owner2.setUser(user2);

        var result = ownerRepository.create(owner2);
        var owner2Result = ownerRepository.findById(owner2.getId());

        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo(owner2Result.get().getName());
    }

    /**
     * Неудачное сохранение владельца в БД.
     */
    @Test
    public void whenCreateOwnerThenOptionalIsEmpty() throws Exception {
        Owner owner2 = new Owner();
        owner2.setName("Ivan");
        owner2.setUser(user);

        var result = ownerRepository.create(owner2);
        var owner2Result = ownerRepository.findById(owner2.getId());

        assertThat(result).isEmpty();
        assertThat(owner2Result).isEmpty();
    }

    /**
     * Владелец найден в БД по id.
     */
    @Test
    public void whenFindOwnerById() throws Exception {
        var result = ownerRepository.findById(owner.getId());

        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo(owner.getName());
    }

    /**
     * Владелец не найден в БД по id.
     */
    @Test
    public void whenNotFoundOwnerById() throws Exception {
        var result = ownerRepository.findById(owner.getId() + 1);

        assertThat(result).isEmpty();
    }

    /**
     * Удачное обновление владельца в БД.
     */
    @Test
    public void whenUpdateOwnerThenTrue() throws Exception {
        String newName = "Igor";
        owner.setName(newName);

        var result = ownerRepository.update(owner);
        var ownerResult = ownerRepository.findById(owner.getId());

        assertThat(result).isTrue();
        assertThat(ownerResult.get().getName()).isEqualTo(newName);
    }

    /**
     * Неудачное обновление владельца в БД.
     */
    @Test
    public void whenUpdateOwnerThenFalse() throws Exception {
        User user2 = new User();
        user2.setLogin("admin2");
        user2.setPassword("admin2");

        Owner owner2 = new Owner();
        owner2.setName("Ivan");
        owner2.setUser(user2);
        ownerRepository.create(owner2);

        owner.setUser(user2);

        var result = ownerRepository.update(owner);
        var ownerResult = ownerRepository.findById(owner.getId());

        assertThat(result).isFalse();
        assertThat(ownerResult.get().getUser().getLogin()).isEqualTo(user.getLogin());
    }

    /**
     * Удачное удаление владельца из БД по id.
     */
    @Test
    public void whenDeleteOwnerThenTrue() throws Exception {
        var result = ownerRepository.delete(owner.getId());
        var ownerResult = ownerRepository.findById(owner.getId());

        assertThat(result).isTrue();
        assertThat(ownerResult).isEmpty();
    }

    /**
     * Неудачное удаление владельца из БД по id.
     */
    @Test
    public void whenDeleteOwnerThenFalse() throws Exception {
        var result = ownerRepository.delete(owner.getId() + 1);
        var ownerResult = ownerRepository.findById(owner.getId());

        assertThat(result).isFalse();
        assertThat(ownerResult).isNotEmpty();
        assertThat(ownerResult.get().getName()).isEqualTo(owner.getName());
    }

    /**
     * Найдены все владельцы в БД.
     */
    @Test
    public void whenFindAllOwnersOrderedById() throws Exception {
        User user2 = new User();
        user2.setLogin("admin2");
        user2.setPassword("admin2");

        Owner owner2 = new Owner();
        owner2.setName("Ivan");
        owner2.setUser(user2);
        ownerRepository.create(owner2);

        var result = ownerRepository.findAllOrderById();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(owner, owner2);
    }

    /**
     * Найдены все владельцы в БД, содержащие в имени "ter".
     */
    @Test
    public void whenFindAllOwnersByLikeNameTer() throws Exception {
        User user2 = new User();
        user2.setLogin("admin2");
        user2.setPassword("admin2");

        Owner owner2 = new Owner();
        owner2.setName("Ivan");
        owner2.setUser(user2);
        ownerRepository.create(owner2);

        var result = ownerRepository.findByLikeName("ter");

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(owner);
    }

    /**
     * Не найдены владельцы в БД, содержащие в имени "man".
     */
    @Test
    public void whenNotFoundOwnersByLikeNameMan() throws Exception {
        var result = ownerRepository.findByLikeName("man");

        assertThat(result).isEmpty();
    }

    /**
     * Найден владелец в БД по имени.
     */
    @Test
    public void whenFindOwnerByNamePeter() throws Exception {
        var result = ownerRepository.findByName("Peter");

        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo(owner.getName());
    }

    /**
     * Не найден владелец в БД по имени.
     */
    @Test
    public void whenNotFoundOwnerByNameIvan() throws Exception {
        var result = ownerRepository.findByName("Ivan");

        assertThat(result).isEmpty();
    }
}