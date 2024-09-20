package ru.job4j.cars.model;

import javax.persistence.*;
import lombok.*;
import lombok.EqualsAndHashCode.Include;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель данных объявление
 * @author Artur Stepanian
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "photos")
@Builder
@Entity
@Table(name = "auto_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private Long id;

    @Include
    private String description;

    /**
     * Цена автомобиля
     */
    private int price;

    /**
     * Город объявления
     */
    private String city;

    @Include
    private LocalDateTime created = LocalDateTime.now();

    private boolean isSold;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "auto_user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Photo> photos = new ArrayList<>();

    public void addPhoto(Photo photo) {
        photos.add(photo);
        photo.setPost(this);
    }
}
