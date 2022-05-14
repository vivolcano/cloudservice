package ru.netology.cloudservice.mapper;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudservice.api.dto.AttachmentDto;
import ru.netology.cloudservice.domain.Attachment;

import java.io.IOException;

/**
 * Конфигурация маппинга MultipartFile.class -> Attachment.class
 * и Attachment.class -> AttachmentDto
 *
 * @author Viktor_Loskutov
 */
@Component
public class AttachmentMapper {

    public Attachment map(MultipartFile file, String fileName) {
        try {
            return new Attachment()
                    .setFileName(fileName)
                    .setFileType(file.getContentType())
                    .setFileSize(file.getSize())
                    .setData(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AttachmentDto map(Attachment attachment) {
        return new AttachmentDto()
                .setFileName(attachment.getFileName())
                .setFileSize(attachment.getFileSize());
    }
}