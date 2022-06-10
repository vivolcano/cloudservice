package ru.netology.cloudservice.controller;

import ru.netology.cloudservice.api.AttachmentListResource;
import ru.netology.cloudservice.api.dto.AttachmentDto;
import ru.netology.cloudservice.service.AttachmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Реализация API для получения информации по всем файлам {@link AttachmentListResource}
 *
 * @author Viktor_Loskutov
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class AttachmentListResourceImpl implements AttachmentListResource {

    private final AttachmentService attachmentService;

    @Override
    public ResponseEntity<List<AttachmentDto>> readAll() {
        log.info("get all - start");
        var result = attachmentService.getAllAttachment();
        log.info("add all - end");
        return ResponseEntity.ok().body(result);
    }
}