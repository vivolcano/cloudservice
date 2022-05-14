package ru.netology.cloudservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Класс для запуска приложения
 *
 * @author Viktor_Loskutov
 */
@Slf4j
@SpringBootApplication
public class CloudServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudServiceApplication.class, args);
    }
}