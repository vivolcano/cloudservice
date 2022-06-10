package ru.netology.cloudservice.service;

import ru.netology.cloudservice.api.dto.TokenDto;
import ru.netology.cloudservice.api.dto.UserDto;
import ru.netology.cloudservice.config.security.jwt.JwtProvider;
import ru.netology.cloudservice.exception.ValidationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Реализация сервиса для получения системного токена {@link TokenService}
 *
 * @author Viktor_Loskutov
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public TokenDto getToken(UserDto userDto) {
        return Optional.ofNullable(userService.findByLoginAndPassword(userDto.getLogin(), userDto.getPassword()))
                .map(user -> jwtProvider.generateToken(user.getLogin()))
                .map(accessToken -> new TokenDto().setAccessToken(accessToken))
                .orElseThrow(() -> new ValidationException("tokenService", "Не верный логин или пароль"));
    }
}