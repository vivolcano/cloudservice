package ru.netology.cloudservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.netology.cloudservice.api.dto.WarningMessage;

/**
 * Обработчик исключений сервиса.
 * Перехватывает и обрабатывает исключения, которые могут возникнуть в процессе общения через RestController.
 *
 * @author Viktor_Loskutov
 */
@Slf4j
@RestControllerAdvice(basePackages = "ru.netology.cloudservice.controller")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public WarningMessage handleRuntimeException(ValidationException exception) {
        log.error(exception.toString());
        return exception.getWarning();
    }
}