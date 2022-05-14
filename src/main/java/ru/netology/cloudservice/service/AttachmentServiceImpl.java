package ru.netology.cloudservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudservice.api.dto.AttachmentDto;
import ru.netology.cloudservice.domain.Attachment;
import ru.netology.cloudservice.mapper.AttachmentMapper;
import ru.netology.cloudservice.repository.AttachmentRepository;

import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.TRUE;
import static java.util.stream.Collectors.toList;

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
    public Attachment getAttachment(String filename) {
        return Optional.of(attachmentRepository.findAttachmentByFileName(filename))
                .orElseThrow(() -> new RuntimeException("Файл не найден"));
    }

    @Override
    public AttachmentDto addAttachment(MultipartFile file) {
        return Optional.of(file)
                .map(MultipartFile::getOriginalFilename)
                .map(name -> mapper.map(file, name))
                .map(attachmentRepository::save)
                .map(mapper::map)
                .orElseThrow(() -> new RuntimeException("Не удалось сохранить файл"));
    }

    @Override
    public AttachmentDto updateAttachment(AttachmentDto attachmentDto, String filename) {
        return Optional.of(attachmentRepository.findAttachmentByFileName(filename))
                .map(attachment -> attachment.setFileName(attachmentDto.getFileName()))
                .map(attachmentRepository::save)
                .map(mapper::map)
                .orElseThrow(() -> new RuntimeException("Файл не найден"));
    }

    @Override
    public boolean deleteAttachment(String filename) {
        return Optional.of(attachmentRepository.findAttachmentByFileName(filename))
                .map(attachment -> attachment.setDeleted(true))
                .map(attachmentRepository::save)
                .map(attachment -> TRUE)
                .orElseThrow(() -> new RuntimeException("Файл не найден"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttachmentDto> getAllAttachment() {
        var attachments = Optional.of(attachmentRepository.findAll())
                .filter(attachmentList -> !attachmentList.isEmpty())
                .orElseThrow(() -> new RuntimeException("Файлов нет"));

        return attachments.stream()
                .map(mapper::map)
                .collect(toList());
    }
}