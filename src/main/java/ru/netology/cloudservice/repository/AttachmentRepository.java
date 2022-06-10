package ru.netology.cloudservice.repository;

import ru.netology.cloudservice.domain.Attachment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Интерфейс для работы с сущностями файлов в БД
 *
 * @author Viktor_Loskutov
 */
public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {

    /**
     * Метод возвращает файл из БД по имени файла и пользователю создавшего его
     *
     * @param fileName имя файла
     * @param createUser текущий пользователь в системе
     *
     * @return объект Optional от файла
     */
    Optional<Attachment> findAttachmentByFileNameAndCreateUser(String fileName, String createUser);

    /**
     * Метод проверяет, существует ли файл с указанным именем в БД
     *
     * @param fileName имя файла
     *
     * @return true - если файл существует, иначе - false
     */
    boolean existsByFileName(String fileName);

    /**
     * Метод возвращает все файлы для пользователя, добавившего их в сервис
     *
     * @param createUser текущий пользователь в системе
     *
     * @return список dto файлов
     */
    List<Attachment> findAllByCreateUser(String createUser);
}