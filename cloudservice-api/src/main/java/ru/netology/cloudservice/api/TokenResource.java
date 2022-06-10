package ru.netology.cloudservice.api;

import ru.netology.cloudservice.api.dto.TokenDto;
import ru.netology.cloudservice.api.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * API для работы с системными токенами
 *
 * @author Viktor_Loskutov
 */
@RequestMapping("/login")
@Tag(name = "API для получения системного токена")
public interface TokenResource {

    @Operation(description = "Получение системного токена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токен получен"),
            @ApiResponse(responseCode = "400", description = "Ошибка ввода данных"),
            @ApiResponse(responseCode = "401", description = "Полномочия не подтверждены. Например, JWT невалиден, отсутствует, либо неверного формата")
    })
    @PostMapping()
    ResponseEntity<TokenDto> getToken(@RequestBody UserDto userDto);
}