package ru.netology.cloudservice.domain;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.UUID;

/**
 * Представление сущности файла в системе
 *
 * @author Viktor_Loskutov
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Where(clause = "deleted = false")
@Table(name = "attachment")
@Entity
public class Attachment extends CreateAtIdentified implements Identified<UUID> {

    private static final long serialVersionUID = -9005741475704378708L;

    /**
     * Уникальный идентификатор файла
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    UUID id;

    /**
     * Имя файла
     */
    @Column(name = "file_name", nullable = false)
    String fileName;

    /**
     * Тип файла
     */
    @Column(name = "file_type", nullable = false)
    String fileType;

    /**
     * Размер файла в байтах
     */
    @Column(name = "file_size", nullable = false)
    long fileSize;

    /**
     * Представление файла в байтах
     */
    @Lob
    byte[] data;

    /**
     * Признак удаления файла
     */
    @Column(name = "deleted", nullable = false)
    boolean deleted;
}