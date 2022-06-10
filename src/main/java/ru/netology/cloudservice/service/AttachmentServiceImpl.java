package ru.netology.cloudservice.service;

import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import ru.netology.cloudservice.api.dto.AttachmentDto;
import ru.netology.cloudservice.domain.Attachment;
import ru.netology.cloudservice.exception.ValidationException;
import ru.netology.cloudservice.mapper.AttachmentMapper;
import ru.netology.cloudservice.repository.AttachmentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с файлами {@link AttachmentService}
 *
 * @author Viktor_Loskutov
 */
@RequiredArgsConstructor
@Transactional
@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Attachment getAttachment(String fileName) {
        var createUser = existUser();

        return attachmentRepository.findAttachmentByFileNameAndCreateUser(fileName, createUser)
                .orElseThrow(() -> new ValidationException("cloudService",
                        format("Файл %s для пользователя %s не найден", fileName, createUser)));
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
        var createUser = existUser();

        return attachmentRepository.findAttachmentByFileNameAndCreateUser(fileName, createUser)
                .map(attachment -> setNewFileName(attachmentDto, attachment))
                .map(attachmentRepository::save)
                .map(mapper::map)
                .orElseThrow(() -> new ValidationException("cloudService",
                        format("Файл %s для пользователя %s не найден", fileName, createUser)));
    }

    @Override
    public boolean deleteAttachment(String fileName) {
        var createUser = existUser();

        return attachmentRepository.findAttachmentByFileNameAndCreateUser(fileName, createUser)
                .map(attachment -> attachment.setDeleted(true))
                .map(attachmentRepository::save)
                .map(attachment -> TRUE)
                .orElseThrow(() -> new ValidationException("cloudService",
                        format("Файл %s для пользователя %s не найден", fileName, createUser)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttachmentDto> getAllAttachment() {
        var createUser = existUser();

        var attachments = Optional.of(attachmentRepository.findAllByCreateUser(createUser))
                .filter(attachmentList -> !attachmentList.isEmpty())
                .orElseThrow(() -> new ValidationException("cloudService", format("Файлы для пользователя %s не найдены", createUser)));

        return attachments.stream()
                .map(mapper::map)
                .collect(toList());
    }

    /**
     * Метод проверяет наличие в БД файла с текущим именем
     *
     * @param file для добавления
     * @return MultipartFile, если имя не существует в БД, иначе - null
     */
    MultipartFile isExistFileName(MultipartFile file) {
        return attachmentRepository.existsByFileName(file.getOriginalFilename()) ? null : file;
    }

    /**
     * Метод устанавливает новое значение для имени файла
     *
     * @param attachmentDto dto файла, с новым именем файла
     * @param attachment    файл для изменения
     * @return Attachment файл с измененным именем
     */
    Attachment setNewFileName(AttachmentDto attachmentDto, Attachment attachment) {
        return attachment.setFileName(attachmentDto.getFileName());
    }

    /**
     * Метод возвращает имя(логин) текущего пользователя в системе
     *
     * @return имя(логин) текущего пользователя
     */
    String existUser() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .orElse(null);
    }
}