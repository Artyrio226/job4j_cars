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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "auto_user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Photo> photos = new ArrayList<>();

    public void addPhoto(Photo photo) {
        photos.add(photo);
        photo.setPost(this);
    }
}
