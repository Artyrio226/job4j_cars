package ru.job4j.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String name;
    private String body;
    private String nameEngine;
    private String transmission;
    private String drive;
    private String color;
    private String nameOwner;
    private String description;
    private int year;
    private int mileage;
}
