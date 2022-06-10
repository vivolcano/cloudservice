package ru.netology.cloudservice.api;

import ru.netology.cloudservice.api.dto.AttachmentDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * API для работы с файлами
 *
 * @author Viktor_Loskutov
 */
@RequestMapping("/file")
@Tag(name = "API для работы с файлами")
public interface AttachmentResource {

    @Operation(description = "Загрузка файла на облако")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл загружен"),
            @ApiResponse(responseCode = "400", description = "Ошибка ввода данных"),
            @ApiResponse(responseCode = "401",
                    description = "Полномочия не подтверждены. Например, JWT невалиден, отсутствует, либо неверного формата")
    })
    @PostMapping("create")
    ResponseEntity<AttachmentDto> create(@RequestParam("filename") MultipartFile file, UriComponentsBuilder componentsBuilder);

    @Operation(description = "Загрузка из облака")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл загружен"),
            @ApiResponse(responseCode = "400", description = "Ошибка ввода данных"),
            @ApiResponse(responseCode = "401", description = "Полномочия не подтверждены. Например, JWT невалиден, отсутствует, либо неверного формата"),
            @ApiResponse(responseCode = "500", description = "Ошибка загрузки файла")
    })
    @GetMapping("read/{filename}")
    ResponseEntity<Resource> read(@PathVariable String filename);

    @Operation(description = "Обновление файла")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл обновлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка ввода данных"),
            @ApiResponse(responseCode = "401", description = "Полномочия не подтверждены. Например, JWT невалиден, отсутствует, либо неверного формата"),
            @ApiResponse(responseCode = "500", description = "Ошибка обновления файла")
    })
    @PutMapping("update/{filename}")
    ResponseEntity<AttachmentDto> update(@RequestBody AttachmentDto attachmentDto, @PathVariable("filename") String filename);


    @Operation(description = "Удаление файла")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл удален"),
            @ApiResponse(responseCode = "400", description = "Ошибка ввода данных"),
            @ApiResponse(responseCode = "401", description = "Полномочия не подтверждены. Например, JWT невалиден, отсутствует, либо неверного формата"),
            @ApiResponse(responseCode = "500", description = "Ошибка удаления файла")
    })
    @DeleteMapping("delete/{filename}")
    ResponseEntity<Void> delete(@PathVariable("filename") String filename);
}