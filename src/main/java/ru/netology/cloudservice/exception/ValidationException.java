package ru.netology.cloudservice.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;
import ru.netology.cloudservice.api.dto.WarningMessage;

/**
 * Исключение, содержащее одну ошибку
 *
 * @author Viktor_Loskutov
 */
@Getter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ValidationException extends RuntimeException {

    @NonNull
    WarningMessage warning;

    public ValidationException(String systemName, String displayMessage) {

        this.warning = new WarningMessage(systemName, displayMessage);
    }
}