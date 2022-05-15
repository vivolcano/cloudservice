package ru.netology.cloudservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler
 *
 * @author Viktor_Loskutov
 */
@Slf4j
@RestControllerAdvice(basePackages = "ru.netology.cloudservice.controller")
public class GlobalExceptionHandler {

    @Value("${spring.application.name}")
    private String systemName;

    //TODO написать обработчик ошибок
}