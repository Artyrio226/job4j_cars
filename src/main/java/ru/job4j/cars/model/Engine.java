package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

/**
 * Модель данных двигатель
 * @author Artur Stepanian
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "engine")
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Объем двигателя
     */
    private double capacity;

    /**
     * Количество лошадиных сил двигателя
     */
    private int hp;

    /**
     * Тип топлива двигателя
     */
    private String fuel;
}
