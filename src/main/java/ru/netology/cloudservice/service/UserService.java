package ru.netology.cloudservice.service;

import ru.netology.cloudservice.domain.User;

import org.springframework.stereotype.Service;

/**
 * Сервис для работы с пользователями
 *
 * @author Viktor_Loskutov
 */
@Service
public interface UserService {

    /**
     * Возвращает пользователя с указанным логином
     *
     * @param login логин пользователя
     * @return пользователь
     */
    User findByLogin(String login);

    /**
     * Возвращает пользователя с указанным логином и паролем
     *
     * @param login    логин пользователя
     * @param password пароль пользователя
     * @return пользователь
     */
    User findByLoginAndPassword(String login, String password);
}