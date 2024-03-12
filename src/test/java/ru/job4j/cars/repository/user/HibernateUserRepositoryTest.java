package ru.job4j.cars.repository.user;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.CrudRepository;

import static org.assertj.core.api.Assertions.*;

class HibernateUserRepositoryTest {
    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    static CrudRepository cr = new CrudRepository(sf);
    static HibernateUserRepository userRepository = new HibernateUserRepository(cr);

    User user;

    @BeforeEach
    public void init() {
        user = new User();
        user.setLogin("admin1");
        user.setPassword("admin1");
        userRepository.create(user);
    }

    @AfterEach
    public void clean() {
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
     * Удачное сохранение пользователя в БД.
     */
    @Test
    public void whenCreateUserThenOptionalIsNotEmpty() throws Exception {
        User user2 = new User();
        user2.setLogin("admin2");
        user2.setPassword("admin2");

        var result = userRepository.create(user2);
        var user2Result = userRepository.findById(user2.getId());

        assertThat(result).isNotEmpty();
        assertThat(result.get().getLogin()).isEqualTo(user2Result.get().getLogin());
    }

    /**
     * Неудачное сохранение пользователя в БД.
     */
    @Test
    public void whenCreateUserThenOptionalIsEmpty() throws Exception {
        User user2 = new User();
        user2.setLogin("admin1");
        user2.setPassword("admin1");

        var result = userRepository.create(user2);
        var user2Result = userRepository.findById(user2.getId());

        assertThat(result).isEmpty();
        assertThat(user2Result).isEmpty();
    }

    /**
     * Пользователь найден в БД по id.
     */
    @Test
    public void whenFindUserById() throws Exception {
        var result = userRepository.findById(user.getId());

        assertThat(result).isNotEmpty();
        assertThat(result.get().getLogin()).isEqualTo(user.getLogin());
    }

    /**
     * Пользователь не найден в БД по id.
     */
    @Test
    public void whenNotFoundUserById() throws Exception {
        var result = userRepository.findById(user.getId() + 1);

        assertThat(result).isEmpty();
    }

    /**
     * Удачное обновление пользователя в БД.
     */
    @Test
    public void whenUpdateUserThenTrue() throws Exception {
        String newLogin = "admin2";
        user.setLogin(newLogin);

        var result = userRepository.update(user);
        var userResult = userRepository.findById(user.getId());

        assertThat(result).isTrue();
        assertThat(userResult.get().getLogin()).isEqualTo(newLogin);
    }

    /**
     * Неудачное обновление пользователя в БД.
     */
    @Test
    public void whenUpdateUserThenFalse() throws Exception {
        User user2 = new User();
        user2.setLogin("admin2");
        user2.setPassword("admin2");
        userRepository.create(user2);

        String oldLogin = user.getLogin();
        String newLogin = "admin2";
        user.setLogin(newLogin);

        var result = userRepository.update(user);
        var userResult = userRepository.findById(user.getId());

        assertThat(result).isFalse();
        assertThat(userResult.get().getLogin()).isEqualTo(oldLogin);
    }

    /**
     * Удачное удаление пользователя из БД по id.
     */
    @Test
    public void whenDeleteUserThenTrue() throws Exception {
        var result = userRepository.delete(user.getId());
        var userResult = userRepository.findById(user.getId());

        assertThat(result).isTrue();
        assertThat(userResult).isEmpty();
    }

    /**
     * Неудачное удаление пользователя из БД по id.
     */
    @Test
    public void whenDeleteUserThenFalse() throws Exception {
        var result = userRepository.delete(user.getId() + 1);
        var userResult = userRepository.findById(user.getId());

        assertThat(result).isFalse();
        assertThat(userResult).isNotEmpty();
        assertThat(userResult.get().getLogin()).isEqualTo(user.getLogin());
    }

    /**
     * Найдены все пользователи в БД.
     */
    @Test
    public void whenFindAllUsersOrderedById() throws Exception {
        User user2 = new User();
        user2.setLogin("admin2");
        user2.setPassword("admin2");
        userRepository.create(user2);

        var result = userRepository.findAllOrderById();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(user, user2);
    }

    /**
     * Найдены все пользователи в БД, содержащие в логине "min".
     */
    @Test
    public void whenFindAllUsersByLikeLoginMin() throws Exception {
        User user2 = new User();
        user2.setLogin("admin2");
        user2.setPassword("admin2");
        userRepository.create(user2);

        var result = userRepository.findByLikeLogin("min");

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(user, user2);
    }

    /**
     * Не найдены пользователи в БД, содержащие в логине "man".
     */
    @Test
    public void whenNotFoundUsersByLikeLoginMan() throws Exception {
        User user2 = new User();
        user2.setLogin("admin2");
        user2.setPassword("admin2");
        userRepository.create(user2);

        var result = userRepository.findByLikeLogin("man");

        assertThat(result).isEmpty();
    }

    /**
     * Найден пользователь в БД по логину.
     */
    @Test
    public void whenFindUserByLoginAdmin1() throws Exception {
        var result = userRepository.findByLogin("admin1");

        assertThat(result).isNotEmpty();
        assertThat(result.get().getLogin()).isEqualTo(user.getLogin());
    }

    /**
     * Не найден пользователь в БД по логину.
     */
    @Test
    public void whenNotFoundUserByLoginAdmin2() throws Exception {
        var result = userRepository.findByLogin("admin2");

        assertThat(result).isEmpty();
    }
}