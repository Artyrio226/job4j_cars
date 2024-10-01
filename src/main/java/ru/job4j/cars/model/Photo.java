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
public class Photo implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String path;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "auto_post_id", nullable = false)
    private Post post;

    /**
     * Устанавливает объявление в поле Post в Photo
     * и фото в список фото объявления Post.
     *
     * @param post пользователь
     */
    public void addPost(Post post) {
        this.post = post;
        this.post.getPhotos().add(this);
    }
}
