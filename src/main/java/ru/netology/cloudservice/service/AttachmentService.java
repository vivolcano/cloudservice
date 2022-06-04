package ru.netology.cloudservice.service;

import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudservice.api.dto.AttachmentDto;
import ru.netology.cloudservice.domain.Attachment;

import java.util.List;

/**
 *
 * Сервис для работы с файлами
 *
 * @author Viktor_Loskutov
 */
public interface AttachmentService {

    /**
     * Получение файла из сервиса
     *
     * @param fileName имя файла
     * @return файл
     */
    Attachment getAttachment(String fileName);

    /**
     * Добавление нового файла
     *
     * @param file с параметрами для создания файла
     */
    AttachmentDto addAttachment(MultipartFile file);

    /**
     * Обновление файла
     *
     * @param attachmentDto dto файла
     * @param fileName новое имя файла
     * @return dto файла
     */
    AttachmentDto updateAttachment(AttachmentDto attachmentDto, String fileName);

    /**
     * Удаление файла
     *
     * @param fileName имя файла
     * @return результат успешно ли выполнена операция
     */
    boolean deleteAttachment(String fileName);

    /**
     * Получение информации по всем файлам
     *
     * @return список с dto файлов
     */
    List<AttachmentDto> getAllAttachment();
}