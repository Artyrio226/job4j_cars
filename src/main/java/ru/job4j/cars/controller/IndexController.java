package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.service.post.PostService;

@Controller
@AllArgsConstructor
@RequestMapping({"/", "index"})
public class IndexController {
    private final PostService simplePostService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("posts", simplePostService.findAllOrderById());
        return "index";
    }
}
