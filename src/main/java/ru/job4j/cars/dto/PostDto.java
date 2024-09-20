package ru.job4j.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private String name;
    private String body;
    private double capacity;
    private int hp;
    private String fuel;
    private String transmission;
    private String color;
    private String username;
    private String description;
    private int year;
    private int mileage;
}
