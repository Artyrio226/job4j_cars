package ru.job4j.cars.model;

import javax.persistence.*;
import lombok.*;
import lombok.EqualsAndHashCode.Include;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "price_history")
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private Long id;
    private long before;
    private long after;
    private LocalDateTime created;
}
