package ru.netology.cloudservice.service;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.cloudservice.api.dto.WarningMessage;
import ru.netology.cloudservice.domain.Attachment;
import ru.netology.cloudservice.exception.ValidationException;
import ru.netology.cloudservice.mapper.AttachmentMapper;
import ru.netology.cloudservice.repository.AttachmentRepository;

import java.util.Optional;

@ExtendWith({MockitoExtension.class})
class AttachmentServiceImplTest {

    @InjectMocks
    @Spy
    AttachmentServiceImpl subj;

    @Mock
    AttachmentRepository attachmentRepository;

    @Mock
    AttachmentMapper mapper;

    private InOrder inOrder;

    @BeforeEach
    void setup() {
        inOrder = inOrder(subj, attachmentRepository, mapper);
    }

    @Test
    void getSuccess() {
        var fileName = randomString();
        var attachment = new Attachment().setFileName(fileName);
        var attachmentOptional = Optional.of(attachment);

        doReturn(attachmentOptional).when(attachmentRepository).findAttachmentByFileName(fileName);

        var actual = subj.getAttachment(fileName);

        assertEquals(attachment, actual);

        inOrder.verify(attachmentRepository, times(1)).findAttachmentByFileName(fileName);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void getNotFound() {
        var fileName = randomString();
        var attachmentOptional = Optional.empty();
        var expected = new WarningMessage("cloudService", format("Файл %s не найден", fileName));

        doReturn(attachmentOptional).when(attachmentRepository).findAttachmentByFileName(fileName);

        var actual = assertThrows(ValidationException.class, () -> subj.getAttachment(fileName));

        assertAll(
                () -> {
                    assertEquals(actual.getWarning().getSystemName(), expected.getSystemName());
                    assertEquals(actual.getWarning().getDisplayMessage(), expected.getDisplayMessage());
                }
        );

        inOrder.verify(attachmentRepository, times(1)).findAttachmentByFileName(fileName);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void updateAttachment() {
    }

    @Test
    void deleteSuccess() {
        var fileName = randomString();
        var attachment = new Attachment().setFileName(fileName);
        var attachmentOptional = Optional.of(attachment);
        var attachmentDeleted = new Attachment().setId(attachment.getId()).setDeleted(true);

        doReturn(attachmentOptional).when(attachmentRepository).findAttachmentByFileName(attachment.getFileName());
        doReturn(attachmentDeleted).when(attachmentRepository).save(attachment);

        var actual = subj.deleteAttachment(attachment.getFileName());

        assertTrue(actual);

        inOrder.verify(attachmentRepository, times(1)).findAttachmentByFileName(attachment.getFileName());
        inOrder.verify(attachmentRepository, times(1)).save(attachment);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void deleteNotFound() {
        var fileName = randomString();
        var attachment = new Attachment().setFileName(fileName);
        var attachmentOptional = Optional.empty();
        var expected = new WarningMessage("cloudService", format("Файл %s не найден", fileName));

        doReturn(attachmentOptional).when(attachmentRepository).findAttachmentByFileName(attachment.getFileName());

        var actual = assertThrows(ValidationException.class, () -> subj.deleteAttachment(fileName));

        assertAll(
                () -> {
                    assertEquals(actual.getWarning().getSystemName(), expected.getSystemName());
                    assertEquals(actual.getWarning().getDisplayMessage(), expected.getDisplayMessage());
                }
        );

        inOrder.verify(attachmentRepository, times(1)).findAttachmentByFileName(attachment.getFileName());
        inOrder.verify(attachmentRepository, never()).save(attachment);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void getAllAttachment() {
    }

    /**
     * Получить случайную строку длинной 10 символов
     *
     * @return строка
     */
    private String randomString() {
        return RandomStringUtils.random(10);
    }
}