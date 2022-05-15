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
     * @param filename имя файла
     * @return файл
     */
    Attachment getAttachment(String filename);

    /**
     * Добавление нового файла
     *
     * @param file с параметрами для создания файла
     */
    AttachmentDto addAttachment(MultipartFile file);

    /**
     * Обновление файла
     *
     * @param attachmentDto dto с параметрами для обновления файла
     * @return dto файла
     */
    AttachmentDto updateAttachment(AttachmentDto attachmentDto, String filename);

    /**
     * Удаление файла
     *
     * @param filename имя файла
     * @return результат успешно ли выполнена операция
     */
    boolean deleteAttachment(String filename);

    /**
     * Получение информации по всем файлам
     *
     * @return список с dto файлов
     */
    List<AttachmentDto> getAllAttachment();
}