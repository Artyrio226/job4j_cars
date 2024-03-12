package ru.job4j.cars.repository.engine;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.CrudRepository;

import static org.assertj.core.api.Assertions.*;

class HibernateEngineRepositoryTest {
    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    static CrudRepository cr = new CrudRepository(sf);
    static HibernateEngineRepository engineRepository = new HibernateEngineRepository(cr);

    Engine engine;


    @BeforeEach
    public void init() {
        engine = new Engine();
        engine.setName("Turbo V8");
        engineRepository.create(engine);
    }

    @AfterEach
    public void clean() {
        var engines = engineRepository.findAllOrderById();
        for (var engine : engines) {
            engineRepository.delete(engine.getId());
        }
    }

    @AfterAll
    public static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    /**
     * Удачное сохранение двигателя в БД.
     */
    @Test
    public void whenCreateEngineThenOptionalIsNotEmpty() throws Exception {
        Engine engine2 = new Engine();
        engine2.setName("Hybrid");

        var result = engineRepository.create(engine2);
        var engine2Result = engineRepository.findById(engine2.getId());

        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo(engine2Result.get().getName());
    }

    /**
     * Неудачное сохранение двигателя в БД.
     */
    @Test
    public void whenCreateEngineThenOptionalIsEmpty() throws Exception {
        Engine engine2 = new Engine();
        engine2.setName(null);

        var result = engineRepository.create(engine2);
        var engine2Result = engineRepository.findById(engine2.getId());

        assertThat(result).isEmpty();
        assertThat(engine2Result).isEmpty();
    }

    /**
     * Двигатель найден в БД по id.
     */
    @Test
    public void whenFindEngineById() throws Exception {
        var result = engineRepository.findById(engine.getId());

        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo(engine.getName());
    }

    /**
     * Двигатель не найден в БД по id.
     */
    @Test
    public void whenNotFoundEngineById() throws Exception {
        var result = engineRepository.findById(engine.getId() + 1);

        assertThat(result).isEmpty();
    }

    /**
     * Удачное обновление двигателя в БД.
     */
    @Test
    public void whenUpdateEngineThenTrue() throws Exception {
        String newName = "Hybrid";
        engine.setName(newName);

        var result = engineRepository.update(engine);
        var engineResult = engineRepository.findById(engine.getId());

        assertThat(result).isTrue();
        assertThat(engineResult.get().getName()).isEqualTo(newName);
    }

    /**
     * Неудачное обновление двигателя в БД.
     */
    @Test
    public void whenUpdateEngineThenFalse() throws Exception {
        String oldName = engine.getName();
        engine.setName(null);

        var result = engineRepository.update(engine);
        var engineResult = engineRepository.findById(engine.getId());

        assertThat(result).isFalse();
        assertThat(engineResult.get().getName()).isEqualTo(oldName);
    }

    /**
     * Удачное удаление двигателя из БД по id.
     */
    @Test
    public void whenDeleteEngineThenTrue() throws Exception {
        var result = engineRepository.delete(engine.getId());
        var engineResult = engineRepository.findById(engine.getId());

        assertThat(result).isTrue();
        assertThat(engineResult).isEmpty();
    }

    /**
     * Неудачное удаление двигателя из БД по id.
     */
    @Test
    public void whenDeleteEngineThenFalse() throws Exception {
        var result = engineRepository.delete(engine.getId() + 1);
        var engineResult = engineRepository.findById(engine.getId());

        assertThat(result).isFalse();
        assertThat(engineResult).isNotEmpty();
        assertThat(engineResult.get().getName()).isEqualTo(engine.getName());
    }

    /**
     * Найдены все двигатели в БД.
     */
    @Test
    public void whenFindAllEnginesOrderedById() throws Exception {
        Engine engine2 = new Engine();
        engine2.setName("Hybrid");
        engineRepository.create(engine2);

        var result = engineRepository.findAllOrderById();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(engine, engine2);
    }

    /**
     * Найдены все двигатели в БД, содержащие в имени "bri".
     */
    @Test
    public void whenFindAllEnginesByLikeNameBri() throws Exception {
        Engine engine2 = new Engine();
        engine2.setName("Hybrid");
        engineRepository.create(engine2);

        var result = engineRepository.findByLikeName("bri");

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(engine2);
    }

    /**
     * Не найдены двигатели в БД, содержащие в имени "man".
     */
    @Test
    public void whenNotFoundEnginesByLikeNameMan() throws Exception {
        var result = engineRepository.findByLikeName("man");

        assertThat(result).isEmpty();
    }

    /**
     * Найден двигатель в БД по имени.
     */
    @Test
    public void whenFindEngineByNameTurboV8() throws Exception {
        var result = engineRepository.findByName("Turbo V8");

        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo(engine.getName());
    }

    /**
     * Не найден двигатель в БД по имени.
     */
    @Test
    public void whenNotFoundEngineByNameTurboV12() throws Exception {
        var result = engineRepository.findByName("Turbo V12");

        assertThat(result).isEmpty();
    }
}