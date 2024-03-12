package ru.job4j.cars.repository.photo;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Photo;
import ru.job4j.cars.repository.CrudRepository;

import static org.assertj.core.api.Assertions.*;

class HibernatePhotoRepositoryTest {
    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    static CrudRepository cr = new CrudRepository(sf);
    static HibernatePhotoRepository photoRepository = new HibernatePhotoRepository(cr);

    private Photo photo;


    @BeforeEach
    public void init() {
        photo = new Photo();
        photo.setName("photo1");
        photo.setPath("files/photo1");
        photoRepository.create(photo);
    }

    @AfterEach
    public void clean() {
        var photos = photoRepository.findAllOrderById();
        for (var photo : photos) {
            photoRepository.delete(photo.getId());
        }
    }

    @AfterAll
    public static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    /**
     * Удачное сохранение фото в БД.
     */
    @Test
    public void whenCreatePhotoThenOptionalIsNotEmpty() throws Exception {
        Photo photo2 = new Photo();
        photo2.setName("photo2");
        photo2.setPath("files/photo2");

        var result = photoRepository.create(photo2);
        var photo2Result = photoRepository.findById(photo2.getId());

        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo(photo2Result.get().getName());
    }

    /**
     * Неудачное сохранение фото в БД.
     */
    @Test
    public void whenCreatePhotoThenOptionalIsEmpty() throws Exception {
        Photo photo2 = new Photo();
        photo2.setName("photo2");
        photo2.setPath("files/photo1");

        var result = photoRepository.create(photo2);
        var photo2Result = photoRepository.findById(photo2.getId());

        assertThat(result).isEmpty();
        assertThat(photo2Result).isEmpty();
    }

    /**
     * Фото найдено в БД по id.
     */
    @Test
    public void whenFindPhotoById() throws Exception {
        var result = photoRepository.findById(photo.getId());

        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo(photo.getName());
    }

    /**
     * Фото не найдено в БД по id.
     */
    @Test
    public void whenNotFoundPhotoById() throws Exception {
        var result = photoRepository.findById(photo.getId() + 1);

        assertThat(result).isEmpty();
    }

    /**
     * Удачное обновление фото в БД.
     */
    @Test
    public void whenUpdatePhotoThenTrue() throws Exception {
        String newName = "photo3";
        photo.setName(newName);

        var result = photoRepository.update(photo);
        var photoResult = photoRepository.findById(photo.getId());

        assertThat(result).isTrue();
        assertThat(photoResult.get().getName()).isEqualTo(newName);
    }

    /**
     * Неудачное обновление фото в БД.
     */
    @Test
    public void whenUpdatePhotoThenFalse() throws Exception {
        String oldName = photo.getName();
        photo.setName(null);

        var result = photoRepository.update(photo);
        var photoResult = photoRepository.findById(photo.getId());

        assertThat(result).isFalse();
        assertThat(photoResult.get().getName()).isEqualTo(oldName);
    }

    /**
     * Удачное удаление фото из БД по id.
     */
    @Test
    public void whenDeletePhotoThenTrue() throws Exception {
        var result = photoRepository.delete(photo.getId());
        var photoResult = photoRepository.findById(photo.getId());

        assertThat(result).isTrue();
        assertThat(photoResult).isEmpty();
    }

    /**
     * Неудачное удаление фото из БД по id.
     */
    @Test
    public void whenDeletePhotoThenFalse() throws Exception {
        var result = photoRepository.delete(photo.getId() + 1);
        var photoResult = photoRepository.findById(photo.getId());

        assertThat(result).isFalse();
        assertThat(photoResult).isNotEmpty();
        assertThat(photoResult.get().getName()).isEqualTo(photo.getName());
    }

    /**
     * Найдены все фото в БД.
     */
    @Test
    public void whenFindAllPhotosOrderedById() throws Exception {
        Photo photo2 = new Photo();
        photo2.setName("photo2");
        photo2.setPath("files/photo2");
        photoRepository.create(photo2);

        var result = photoRepository.findAllOrderById();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(photo, photo2);
    }

    /**
     * Найдены все фото в БД, содержащие в имени "to1".
     */
    @Test
    public void whenFindAllPhotosByLikeNameBri() throws Exception {
        Photo photo2 = new Photo();
        photo2.setName("photo2");
        photo2.setPath("files/photo2");
        photoRepository.create(photo2);

        var result = photoRepository.findByLikeName("to1");

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(photo);
    }

    /**
     * Не найдены фото в БД, содержащие в имени "man".
     */
    @Test
    public void whenNotFoundPhotosByLikeNameMan() throws Exception {
        var result = photoRepository.findByLikeName("man");

        assertThat(result).isEmpty();
    }

    /**
     * Найдено фото в БД по имени.
     */
    @Test
    public void whenFindPhotoByNameTurboV8() throws Exception {
        var result = photoRepository.findByName("photo1");

        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo(photo.getName());
    }

    /**
     * Не найдено фото в БД по имени.
     */
    @Test
    public void whenNotFoundPhotoByNameTurboV12() throws Exception {
        var result = photoRepository.findByName("Turbo V12");

        assertThat(result).isEmpty();
    }
}