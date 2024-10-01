package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

/**
 * Модель данных автомобиль
 * @author Artur Stepanian
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "engine")
@ToString(exclude = "engine")
@Builder
@Entity
@Table(name = "car")
public class Car implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int years;
    private int mileage;
    private String color;

    @Enumerated(EnumType.STRING)
    private Body body;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id")
    private Engine engine;
}
