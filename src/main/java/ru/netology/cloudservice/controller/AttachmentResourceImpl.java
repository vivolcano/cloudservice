package ru.netology.cloudservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import ru.netology.cloudservice.api.AttachmentResource;
import ru.netology.cloudservice.api.dto.AttachmentDto;
import ru.netology.cloudservice.service.AttachmentService;

/**
 * Реализация API для работы с файлами {@link AttachmentResource}
 *
 * @author Viktor_Loskutov
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
public class AttachmentResourceImpl implements AttachmentResource {

    AttachmentService attachmentService;

    @Override
    public ResponseEntity<AttachmentDto> create(MultipartFile file, UriComponentsBuilder componentsBuilder) {
        log.info("add with {} - start ", file);
        var result = attachmentService.addAttachment(file);
        var uri = componentsBuilder.path("/file/read/" + result.getFileName()).buildAndExpand(result).toUri();
        log.info("add with {} - end", result);
        return ResponseEntity.created(uri).body(result);
    }

    @Override
    public ResponseEntity<Resource> read(String filename) {
        log.info("get with {} - start ", filename);
        var result = attachmentService.getAttachment(filename);

        log.info("get end with {}, with result {}", filename, result);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(result.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "filename=\"" + result.getFileName()
                                + "\"")
                .body(new ByteArrayResource(result.getData()));
    }

    @Override
    public ResponseEntity<AttachmentDto> update(AttachmentDto attachmentDto, String filename) {
        log.info("update with {} - start ", attachmentDto);
        var result = attachmentService.updateAttachment(attachmentDto, filename);
        log.info("update with {} - end", result);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> delete(String filename) {
        log.info("delete with {} - start ", filename);
        attachmentService.deleteAttachment(filename);
        log.info("delete end with {}", filename);
        return ResponseEntity.ok().build();
    }
}