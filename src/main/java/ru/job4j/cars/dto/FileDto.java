package ru.job4j.cars.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private String name;
    private byte[] content;
}
