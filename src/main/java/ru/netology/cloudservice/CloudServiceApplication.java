package ru.netology.cloudservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Класс для запуска приложения
 *
 * @author Viktor_Loskutov
 */
@Slf4j
@EntityScan(basePackages = {"ru.netology.cloudservice.domain", "ru.netology.cloudservice.config.database.converter"})
@SpringBootApplication
public class CloudServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudServiceApplication.class, args);
    }
}