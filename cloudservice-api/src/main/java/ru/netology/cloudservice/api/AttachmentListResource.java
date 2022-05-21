package ru.netology.cloudservice.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.netology.cloudservice.api.dto.AttachmentDto;

import java.util.List;

/**
 * API для получения информации по всем файлам
 *
 * @author Viktor_Loskutov
 */
@RequestMapping("/list")
@Api(value = "API получения информации по всем файлам")
public interface AttachmentListResource {

    @ApiOperation(value = "Получение всех файлов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Файлы получены", response = AttachmentDto.class, responseContainer = "AttachmentDto"),
            @ApiResponse(code = 400, message = "Ошибка ввода данных", response = RuntimeException.class),
            @ApiResponse(code = 401,
                    message = "Полномочия не подтверждены. Например, JWT невалиден, отсутствует, либо неверного формата",
                    response = RuntimeException.class),
            @ApiResponse(code = 500, message = "Ошибка получения информации по файлам",
                    response = RuntimeException.class)
    })
    @GetMapping("readAll/")
    ResponseEntity<List<AttachmentDto>> readAll();
}