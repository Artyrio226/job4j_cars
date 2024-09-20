package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель данных пользователь
 * @author Artur Stepanian
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "posts")
@ToString(exclude = "posts")
@Builder
@Entity
@Table(name = "auto_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя
     */
    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 5, message = "Имя пользователя должно быть не менее 5 символов")
    private String username;

    /**
     *  Адрес электронной почты пользователя
     */
    @Column(unique = true)
    @NotBlank(message = "Поле не должно быть пустым")
    private String email;

    /**
     * Пароль пользователя
     */
    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 4, message = "Пароль пользователя должен быть не менее 4 символов")
    private String password;

    /**
     * Телефонный номер пользователя
     */
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "\\+\\d{11}",
            message = "Должно быть в формате \"+\" и 11 значный номер")
    private String phoneNumber;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
}