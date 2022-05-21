package ru.netology.cloudservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.cloudservice.domain.Attachment;

import java.util.Optional;
import java.util.UUID;

/**
 * Интерфейс для работы с сущностями файлов в БД
 *
 * @author Viktor_Loskutov
 */
public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
    Optional<Attachment> findAttachmentByFileName(String fileName);
    boolean existsByFileName(String fileName);;
}