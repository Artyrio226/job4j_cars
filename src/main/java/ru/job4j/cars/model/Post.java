package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель данных объявление
 * @author Artur Stepanian
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "photos")
@ToString(exclude = "photos")
@Builder
@Entity
@Table(name = "auto_post")
public class Post implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя в объявления
     */
    private String name;

    /**
     * Описание в объявлении
     */
    private String description;

    /**
     * Цена автомобиля
     */
    private int price;

    /**
     * Город объявления
     */
    private String city;

    /**
     * Время создания объявления
     */
    private LocalDateTime created = LocalDateTime.now();

    /**
     * Продано или нет
     */
    @Column(name = "is_sold")
    private boolean isSold;

    /**
     * Новое авто или нет
     */
    @Column(name = "is_new_car")
    private boolean isNewCar;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "auto_user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Photo> photos = new ArrayList<>();

    /**
     * Устанавливает пользователя в поле User в Post
     * и объявление в список объявлений пользователя User.
     *
     * @param user пользователь
     */
    public void addUser(User user) {
        this.user = user;
        this.user.getPosts().add(this);
    }
}
