package ru.netology.cloudservice.api;

import ru.netology.cloudservice.api.dto.AttachmentDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * API для получения информации по всем файлам
 *
 * @author Viktor_Loskutov
 */
@RequestMapping("/list")
@Tag(name = "API получения информации по всем файлам")
public interface AttachmentListResource {

    @Operation(description = "Получение всех файлов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файлы получены"),
            @ApiResponse(responseCode = "400", description = "Ошибка ввода данных"),
            @ApiResponse(responseCode = "401", description = "Полномочия не подтверждены. Например, JWT невалиден, отсутствует, либо неверного формата"),
            @ApiResponse(responseCode = "500", description = "Ошибка получения информации по файлам")
    })
    @GetMapping("readAll/")
    ResponseEntity<List<AttachmentDto>> readAll();
}