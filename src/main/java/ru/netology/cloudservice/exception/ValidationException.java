package ru.netology.cloudservice.exception;

import ru.netology.cloudservice.api.dto.WarningMessage;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;

/**
 * Исключение, содержащее одну ошибку
 *
 * @author Viktor_Loskutov
 */
@Getter
@Accessors(chain = true)
public class ValidationException extends RuntimeException {

    /**
     * Базовая модель описание единичной ошибки
     */
    @NonNull
    private final WarningMessage warning;

    public ValidationException(String systemName, String displayMessage) {
        this.warning = new WarningMessage(systemName, displayMessage);
    }
}