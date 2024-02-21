package ru.job4j.lombok;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder(builderMethodName = "of")
public class Permission {
    private int id;
    private String name;
    @Singular
    private List<String> rules;
}