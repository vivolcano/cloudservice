package ru.netology.cloudservice.service;

import ru.netology.cloudservice.api.dto.TokenDto;
import ru.netology.cloudservice.api.dto.UserDto;

/**
 * Сервис для получения системного токена
 *
 * @author Viktor_Loskutov
 */
public interface TokenService {

    /**
     * Получение системного токена
     *
     * @param userDto dto файла
     * @return dto токена
     */
    TokenDto getToken(UserDto userDto);
}