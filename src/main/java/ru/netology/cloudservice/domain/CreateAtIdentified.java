package ru.netology.cloudservice.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

/**
 * Базовый класс для идентифицируемых объектов таблиц,
 * автоматически заполняет необходимые служебные поля.
 * Для отслеживания изменений (время и пользователь, изменивший объект)
 * использует EntityListener {@link AuditingEntityListener}
 *
 * @author Viktor_Loskutov
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
@MappedSuperclass
public abstract class CreateAtIdentified {

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    Instant createdAt;

    @CreatedBy
    @Column(name = "create_user", nullable = false, length = 255)
    String createUser = "";

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    Instant updatedAt;

    @LastModifiedBy
    @Column(name = "last_modify_user", nullable = false, length = 255)
    String lastModifyUser = "";
}