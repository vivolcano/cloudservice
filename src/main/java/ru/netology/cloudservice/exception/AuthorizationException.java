package ru.netology.cloudservice.exception;

import ru.netology.cloudservice.api.dto.WarningMessage;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;

/**
 * Исключение, содержащее одну ошибку безопасности
 *
 * @author Viktor_Loskutov
 */
@Getter
@Accessors(chain = true)
public class AuthorizationException extends RuntimeException {

    /**
     * Базовая модель описание единичной ошибки авторизации
     */
    @NonNull
    private final WarningMessage warning;

    public AuthorizationException(String systemName, String displayMessage) {
        this.warning = new WarningMessage(systemName, displayMessage);
    }
}