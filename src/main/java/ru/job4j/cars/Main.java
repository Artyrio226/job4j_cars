package ru.job4j.cars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс для запуска приложения
 * @author Artur Stepanian
 * @version 1.0
 */
@Slf4j
@SpringBootApplication
public class Main {

    /**
     * Выполняет запуск приложения
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        log.info("Before Starting application");
        SpringApplication.run(Main.class, args);
        log.debug("Starting my application in debug with {} args", args.length);
        log.info("Starting my application with {} args.", args.length);
        System.out.println("Go to http://localhost:8080");
    }
}
