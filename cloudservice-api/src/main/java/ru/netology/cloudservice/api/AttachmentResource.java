package ru.netology.cloudservice.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import ru.netology.cloudservice.api.dto.AttachmentDto;

/**
 * API для работы с файлами
 *
 * @author Viktor_Loskutov
 */
@RequestMapping("/file")
@Api(value = "API для работы с файлами")
public interface AttachmentResource {

    @ApiOperation(value = "Загрузка файла на облако")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Файл загружен", response = AttachmentDto.class, responseContainer = "AttachmentDto"),
            @ApiResponse(code = 400, message = "Ошибка ввода данных", response = RuntimeException.class),
            @ApiResponse(code = 401,
                    message = "Полномочия не подтверждены. Например, JWT невалиден, отсутствует, либо неверного формата",
                    response = RuntimeException.class)
    })
    @PostMapping("create")
    ResponseEntity<AttachmentDto> create(@RequestParam("filename") MultipartFile file, UriComponentsBuilder componentsBuilder);

    @ApiOperation(value = "Загрузка из облака")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Файл загружен", response = AttachmentDto.class, responseContainer = "AttachmentDto"),
            @ApiResponse(code = 400, message = "Ошибка ввода данных", response = RuntimeException.class),
            @ApiResponse(code = 401,
                    message = "Полномочия не подтверждены. Например, JWT невалиден, отсутствует, либо неверного формата",
                    response = RuntimeException.class),
            @ApiResponse(code = 500, message = "Ошибка загрузки файла",
                    response = RuntimeException.class)
    })
    @GetMapping("read/{filename}")
    ResponseEntity<Resource> read(@PathVariable String filename);

    @ApiOperation(value = "Обновление файла")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Файл обновлен", response = AttachmentDto.class, responseContainer = "AttachmentDto"),
            @ApiResponse(code = 400, message = "Ошибка ввода данных", response = RuntimeException.class),
            @ApiResponse(code = 401,
                    message = "Полномочия не подтверждены. Например, JWT невалиден, отсутствует, либо неверного формата",
                    response = RuntimeException.class),
            @ApiResponse(code = 500, message = "Ошибка обновления файла",
                    response = RuntimeException.class)
    })
    @PutMapping("update/{filename}")
    ResponseEntity<AttachmentDto> update(@RequestBody AttachmentDto attachmentDto, @PathVariable("filename") String filename);


    @ApiOperation(value = "Удаление файла")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Файл удален", response = AttachmentDto.class, responseContainer = "AttachmentDto"),
            @ApiResponse(code = 400, message = "Ошибка ввода данных", response = RuntimeException.class),
            @ApiResponse(code = 401,
                    message = "Полномочия не подтверждены. Например, JWT невалиден, отсутствует, либо неверного формата",
                    response = RuntimeException.class),
            @ApiResponse(code = 500, message = "Ошибка удаления файла",
                    response = RuntimeException.class)
    })
    @DeleteMapping("delete/{filename}")
    ResponseEntity<Void> delete(@PathVariable("filename") String filename);
}