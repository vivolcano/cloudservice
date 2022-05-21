package ru.netology.cloudservice.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * Описание единичной ошибки
 *
 * @author Viktor_Loskutov
 */
@Getter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(description = "Базовая модель описание единичной ошибки")
public final class WarningMessage implements Serializable {

    private static final long serialVersionUID = -9005741475704378708L;

    /**
     * Идентификатор системы, которая сформировала ошибку
     */
    @ApiModelProperty(value = "Идентификатор системы, которая сформировала ошибку", example = "invalid", required = true)
    String systemName;

    /**
     * Сообщение об ошибке для отображения пользователю
     */
    @ApiModelProperty(value = "Сообщение об ошибке для отображения пользователю", example = "Файл picture.jpg не найден", required = true)
    final
    String displayMessage;

    @JsonCreator
    public WarningMessage(@Nullable @JsonProperty("systemName") String systemName, @Nullable @JsonProperty("displayMessage") String displayMessage) {
        this.systemName = systemName;
        this.displayMessage = displayMessage;
    }
}