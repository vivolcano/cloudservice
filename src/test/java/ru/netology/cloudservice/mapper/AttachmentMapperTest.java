package ru.netology.cloudservice.mapper;

import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudservice.api.dto.AttachmentDto;
import ru.netology.cloudservice.domain.Attachment;
import ru.netology.cloudservice.repository.AttachmentRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootTest
@ExtendWith({MockitoExtension.class, SpringExtension.class})
class AttachmentMapperTest {

    @Autowired
    AttachmentMapper mapper;

    @MockBean
    AttachmentRepository attachmentRepository;

    @Test
    void multipartFileToAttachmentMapperTest() throws IOException {
        var file = getMultipartFile();

        var expected = new Attachment()
                .setFileName(file.getOriginalFilename())
                .setFileType(file.getContentType())
                .setFileSize(file.getSize())
                .setData(file.getBytes());

        var actual = mapper.map(file);

        assertAll(
                () -> {
                    assertEquals(actual.getFileName(), expected.getFileName());
                    assertEquals(actual.getFileType(), expected.getFileType());
                    assertEquals(actual.getFileSize(), expected.getFileSize());
                    assertArrayEquals(actual.getData(), expected.getData());
                }
        );
    }

    @Test
    void multipartFileToAttachmentMapperFailTest() throws IOException {
        //TODO
    }

    @MethodSource("getAttachments")
    @ParameterizedTest
    void attachmentToAttachmentDtoMapperTest(Attachment attachment) {

        var expected = new AttachmentDto()
                .setFileName(attachment.getFileName())
                .setFileSize(attachment.getFileSize());

        var actual = mapper.map(attachment);

        assertAll(
                () -> {
                    assertEquals(actual.getFileName(), expected.getFileName());
                    assertEquals(actual.getFileSize(), expected.getFileSize());
                }
        );
    }

    private static Stream<Arguments> getAttachments() {
        return Stream.of(
                Arguments.of(new Attachment()),
                Arguments.of(getAttachment())
        );
    }

    private static Attachment getAttachment() {
        byte[] data = new byte[20];
        new Random().nextBytes(data);

        return new Attachment()
                .setFileName(randomString())
                .setFileType(randomString())
                .setFileSize(nextLong())
                .setData(data);
    }

    //TODO описание
    private static MultipartFile getMultipartFile() {
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
                return 1000;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
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

    private static String randomString() {
        return RandomStringUtils.random(10, true, false);
    }
}