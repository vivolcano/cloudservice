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
 * Представление модели пользователя в системе
 *
 * @author Viktor_Loskutov
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Schema(name = "UserDto", description = "Модель пользователя в системе")
public class UserDto {

    @Schema(name = "login", description = "Логин пользователя",example = "login", required = true)
    String login;

    @Schema(name = "password", description = "Пароль пользователя", example = "password", required = true)
    String password;
}