package ru.job4j.lombok;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode(exclude = {"name"})
@RequiredArgsConstructor
public class Category {
    private final int id;
    @Setter
    private String name;
}