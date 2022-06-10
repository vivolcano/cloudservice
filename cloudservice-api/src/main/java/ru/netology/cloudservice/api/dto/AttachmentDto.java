package ru.netology.cloudservice.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * Представление модели файла в системе
 *
 * @author Viktor_Loskutov
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Schema(name = "AttachmentDto", description = "Модель файла в системе")
public class AttachmentDto {

    @Schema(name = "Имя файла", example = "picture.jpg", required = true)
    String fileName;

    @Schema(name = "Размер файла в байтах", example = "15467", nullable = true)
    long fileSize;
}