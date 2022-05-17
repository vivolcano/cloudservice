package ru.netology.cloudservice.service;

import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudservice.api.dto.AttachmentDto;
import ru.netology.cloudservice.domain.Attachment;
import ru.netology.cloudservice.exception.ValidationException;
import ru.netology.cloudservice.mapper.AttachmentMapper;
import ru.netology.cloudservice.repository.AttachmentRepository;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с файлами {@link AttachmentService}
 *
 * @author Viktor_Loskutov
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
@Service
public class AttachmentServiceImpl implements AttachmentService {

    AttachmentRepository attachmentRepository;
    AttachmentMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Attachment getAttachment(String fileName) {
        return attachmentRepository.findAttachmentByFileName(fileName)
                .orElseThrow(() -> new ValidationException("cloudService",
                        format("Файл %s не найден", fileName)));
    }

    @Override
    public AttachmentDto addAttachment(MultipartFile file) {
        return Optional.of(file)
                .map(this::isExistFileName)
                .map(mapper::map)
                .map(attachmentRepository::save)
                .map(mapper::map)
                .orElseThrow(() -> new ValidationException("cloudService", format(
                        "Файл с указанным именем %s уже существует", file.getOriginalFilename())));
    }

    @Override
    public AttachmentDto updateAttachment(AttachmentDto attachmentDto, String fileName) {
        return attachmentRepository.findAttachmentByFileName(fileName)
                .map(attachment -> setNewFileName(attachmentDto, attachment))
                .map(attachmentRepository::save)
                .map(mapper::map)
                .orElseThrow(() -> new ValidationException("cloudService",
                        format("Файл %s не найден", fileName)));
    }

    @Override
    public boolean deleteAttachment(String fileName) {
        return attachmentRepository.findAttachmentByFileName(fileName)
                .map(attachment -> attachment.setDeleted(true))
                .map(attachmentRepository::save)
                .map(attachment -> TRUE)
                .orElseThrow(() -> new ValidationException("cloudService",
                        format("Файл %s не найден", fileName)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttachmentDto> getAllAttachment() {
        var attachments = Optional.of(attachmentRepository.findAll())
                .filter(attachmentList -> !attachmentList.isEmpty())
                .orElseThrow(() -> new ValidationException("cloudService", "Файлы не найдены"));

        return attachments.stream()
                .map(mapper::map)
                .collect(toList());
    }

    /**
     * Метод проверяет, существует ли в базе данных файл с текущем именем.
     * Если файл с текущем именем не существует, то он возвращаеться, иначе null.
     */
    MultipartFile isExistFileName(MultipartFile file) {
        return attachmentRepository.existsByFileName(file.getOriginalFilename()) ? null : file;
    }

    /**
     * Метод устанавливает новое значение для имени файла
     */
    Attachment setNewFileName(AttachmentDto attachmentDto, Attachment attachment) {
        return attachment.setFileName(attachmentDto.getFileName());
    }
}