package ru.netology.cloudservice.repository;

import ru.netology.cloudservice.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Интерфейс для работы с сущностями пользователей в БД
 *
 * @author Viktor_Loskutov
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Метод возвращает пользователя из БД по логину
     *
     * @param login логин пользователя
     * @return пользователь
     */
    User findByLogin(String login);
}