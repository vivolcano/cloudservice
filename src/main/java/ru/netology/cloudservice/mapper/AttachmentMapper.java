package ru.netology.cloudservice.mapper;

import ru.netology.cloudservice.api.dto.AttachmentDto;
import ru.netology.cloudservice.domain.Attachment;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Конфигурация маппинга MultipartFile.class -> Attachment.class
 * и Attachment.class -> AttachmentDto.class
 *
 * @author Viktor_Loskutov
 */
@Component
public class AttachmentMapper {

    /**
     * Маппинг {@link MultipartFile} в {@link Attachment}
     *
     * @param file
     * @return attachment
     */
    public Attachment map(MultipartFile file) {
        try {
            return new Attachment()
                    .setFileName(file.getOriginalFilename())
                    .setFileType(file.getContentType())
                    .setFileSize(file.getSize())
                    .setData(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Маппинг {@link Attachment} в {@link AttachmentDto}
     *
     * @param attachment
     * @return dto
     */
    public AttachmentDto map(Attachment attachment) {
        return new AttachmentDto()
                .setFileName(attachment.getFileName())
                .setFileSize(attachment.getFileSize());
    }
}