package ru.netology.cloudservice.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * Представление сущности файла в системе
 *
 * @author Viktor_Loskutov
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@ApiModel(description = "Модель файла в системе")
public class AttachmentDto {

    @ApiModelProperty(value = "Имя файла", example = "picture.jpg", required = true)
    String fileName;

    @ApiModelProperty(value = "Размер файла в байтах", example = "15467", allowEmptyValue = true)
    long fileSize;
}