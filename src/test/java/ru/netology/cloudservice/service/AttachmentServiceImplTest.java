package ru.netology.cloudservice.service;

import static java.lang.String.format;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudservice.api.dto.AttachmentDto;
import ru.netology.cloudservice.api.dto.WarningMessage;
import ru.netology.cloudservice.domain.Attachment;
import ru.netology.cloudservice.exception.ValidationException;
import ru.netology.cloudservice.mapper.AttachmentMapper;
import ru.netology.cloudservice.repository.AttachmentRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * AttachmentServiceImplTest
 *
 * @author Viktor_Loskutov
 */
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
    void getFail() {
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
    public void addSuccess() throws IOException {
        var file = getMultipartFile();
        var attachment = new Attachment()
                .setId(randomUUID())
                .setFileName(file.getOriginalFilename())
                .setFileType(file.getContentType())
                .setFileSize(file.getSize())
                .setData(file.getBytes());

        var expected = new AttachmentDto().setFileName(getMultipartFile().getOriginalFilename());

        doReturn(file).when(subj).isExistFileName(file);
        doReturn(attachment).when(mapper).map(file);
        doReturn(attachment).when(attachmentRepository).save(attachment);
        doReturn(expected).when(mapper).map(attachment);

        var actual = subj.addAttachment(file);

        assertEquals(expected, actual);

        inOrder.verify(subj, times(1)).isExistFileName(file);
        inOrder.verify(mapper, times(1)).map(file);
        inOrder.verify(attachmentRepository, times(1)).save(attachment);
        inOrder.verify(mapper, times(1)).map(attachment);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void addFail() throws IOException {
        var file = getMultipartFile();
        var attachment = new Attachment()
                .setId(randomUUID())
                .setFileName(file.getOriginalFilename())
                .setFileType(file.getContentType())
                .setFileSize(file.getSize())
                .setData(file.getBytes());

        var expected = new WarningMessage("cloudService",
                format("Файл с указанным именем %s уже существует", file.getOriginalFilename()));

        doReturn(null).when(subj).isExistFileName(file);

        var actual = assertThrows(ValidationException.class, () -> subj.addAttachment(file));

        assertAll(
                () -> {
                    assertEquals(actual.getWarning().getSystemName(), expected.getSystemName());
                    assertEquals(actual.getWarning().getDisplayMessage(), expected.getDisplayMessage());
                }
        );

        inOrder.verify(subj, times(1)).isExistFileName(file);
        inOrder.verify(mapper, never()).map(file);
        inOrder.verify(attachmentRepository, never()).save(attachment);
        inOrder.verify(mapper, never()).map(attachment);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void updateSuccess() {
        var fileName = randomString();
        var attachmentDto = new AttachmentDto().setFileName(randomString());
        var attachment = new Attachment()
                .setId(randomUUID())
                .setFileName(fileName);

        var renameAttachment = new Attachment()
                .setId(randomUUID())
                .setFileName(attachmentDto.getFileName());

        var attachmentOptional = Optional.of(attachment);

        var expected = new AttachmentDto().setFileName(attachmentDto.getFileName());

        doReturn(attachmentOptional).when(attachmentRepository).findAttachmentByFileName(fileName);
        doReturn(renameAttachment).when(subj).setNewFileName(attachmentDto, attachment);
        doReturn(renameAttachment).when(attachmentRepository).save(renameAttachment);
        doReturn(expected).when(mapper).map(renameAttachment);

        var actual = subj.updateAttachment(attachmentDto, fileName);

        assertEquals(expected, actual);

        inOrder.verify(attachmentRepository, times(1)).findAttachmentByFileName(fileName);
        inOrder.verify(subj, times(1)).setNewFileName(attachmentDto, attachment);
        inOrder.verify(attachmentRepository, times(1)).save(renameAttachment);
        inOrder.verify(mapper, times(1)).map(renameAttachment);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void updateFail() {
        var fileName = randomString();
        var attachmentDto = new AttachmentDto();
        var attachment = new Attachment();
        var renameAttachment = new Attachment()
                .setId(randomUUID())
                .setFileName(attachmentDto.getFileName());

        var expected = new WarningMessage("cloudService",
                format("Файл %s не найден", fileName));

        doReturn(Optional.empty()).when(attachmentRepository).findAttachmentByFileName(fileName);

        var actual = assertThrows(ValidationException.class, () -> subj.updateAttachment(attachmentDto, fileName));

        assertAll(
                () -> {
                    assertEquals(actual.getWarning().getSystemName(), expected.getSystemName());
                    assertEquals(actual.getWarning().getDisplayMessage(), expected.getDisplayMessage());
                }
        );

        inOrder.verify(attachmentRepository, times(1)).findAttachmentByFileName(fileName);
        inOrder.verify(subj, never()).setNewFileName(attachmentDto, attachment);
        inOrder.verify(attachmentRepository, never()).save(renameAttachment);
        inOrder.verify(mapper, never()).map(renameAttachment);
        inOrder.verifyNoMoreInteractions();
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
    void deleteFail() {
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
    void getAllSuccess() {
        var attachments = List.of(
                new Attachment().setFileName(randomString()),
                new Attachment().setFileName(randomString())
        );

        var actual = List.of(
                new AttachmentDto().setFileName(attachments.get(0).getFileName()),
                new AttachmentDto().setFileName(attachments.get(1).getFileName())
        );

        doReturn(attachments).when(attachmentRepository).findAll();
        doReturn(actual.get(0)).when(mapper).map(attachments.get(0));
        doReturn(actual.get(1)).when(mapper).map(attachments.get(1));

        var expected = subj.getAllAttachment();

        assertIterableEquals(actual, expected);

        inOrder.verify(attachmentRepository, times(1)).findAll();
        inOrder.verify(mapper, times(1)).map(attachments.get(0));
        inOrder.verify(mapper, times(1)).map(attachments.get(1));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void getAllFail() {
        var attachment = List.of();
        var expected = new WarningMessage("cloudService", "Файлы не найдены");

        doReturn(attachment).when(attachmentRepository).findAll();

        var actual = assertThrows(ValidationException.class, () -> subj.getAllAttachment());

        assertAll(
                () -> {
                    assertEquals(actual.getWarning().getSystemName(), expected.getSystemName());
                    assertEquals(actual.getWarning().getDisplayMessage(), expected.getDisplayMessage());
                }
        );

        inOrder.verify(attachmentRepository, times(1)).findAll();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void isExistFileNameSuccess() {
        var actual = getMultipartFile();
        var fileName = actual.getOriginalFilename();

        doReturn(false).when(attachmentRepository).existsByFileName(fileName);

        var expected = subj.isExistFileName(actual);

        assertEquals(actual, expected);

        inOrder.verify(attachmentRepository, times(1)).existsByFileName(fileName);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void isExistFileNameFail() {
        var actual = getMultipartFile();
        var fileName = actual.getOriginalFilename();

        doReturn(true).when(attachmentRepository).existsByFileName(fileName);

        var expected = subj.isExistFileName(actual);

        assertNull(expected);

        inOrder.verify(attachmentRepository, times(1)).existsByFileName(fileName);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void setNewFileName() {
        var attachmentDto = new AttachmentDto()
                .setFileName(randomString());

        var attachment = new Attachment()
                .setId(randomUUID())
                .setFileName(randomString())
                .setFileType(randomString())
                .setFileSize(nextLong());

        var expected = attachment.setFileName(attachmentDto.getFileName());
        var actual = subj.setNewFileName(attachmentDto, attachment);

        assertEquals(expected, actual);

        inOrder.verify(subj, times(1)).setNewFileName(attachmentDto, attachment);
        inOrder.verifyNoMoreInteractions();
    }

    /**
     * Получить случайную строку длинной 10 символов
     *
     * @return строка
     */
    private String randomString() {
        return RandomStringUtils.random(10, true, false);
    }

    private MultipartFile getMultipartFile() {
        return new MultipartFile() {
            @Override
            public String getName() {
                return "string";
            }

            @Nullable
            @Override
            public String getOriginalFilename() {
                return "string";
            }

            @Nullable
            @Override
            public String getContentType() {
                return "image/jpeg";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return nextLong();
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
            }
        };
    }
}