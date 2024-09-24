package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

/**
 * Модель данных фото
 * @author Artur Stepanian
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "post")
@ToString(exclude = "post")
@Builder
@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String path;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "auto_post_id", nullable = false)
    private Post post;
}
