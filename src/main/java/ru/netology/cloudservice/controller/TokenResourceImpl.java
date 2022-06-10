package ru.netology.cloudservice.controller;

import ru.netology.cloudservice.api.TokenResource;
import ru.netology.cloudservice.api.dto.TokenDto;
import ru.netology.cloudservice.api.dto.UserDto;
import ru.netology.cloudservice.service.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Реализация API для получения системного токена {@link TokenResource}
 *
 * @author Viktor_Loskutov
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class TokenResourceImpl implements TokenResource {

    private final TokenService tokenService;

    @Override
    public ResponseEntity<TokenDto> getToken(UserDto userDto) {
        log.info("get all - start");
        var result = tokenService.getToken(userDto);
        log.info("add all - end");
        return ResponseEntity.ok().body(result);
    }
}