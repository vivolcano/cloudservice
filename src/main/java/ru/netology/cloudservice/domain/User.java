package ru.netology.cloudservice.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Представление сущности пользователя в системе
 *
 * @author Viktor_Loskutov
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
@Entity
public class User extends CreateAtIdentified implements Identified<UUID> {

    /**
     * Уникальный идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    UUID id;

    /**
     * Логин пользователя
     */
    @Column(length = 20, nullable = false)
    String login;

    /**
     * Пароль пользователя
     */
    @Column(length = 65, nullable = false)
    String password;

    /**
     * Роль пользователя в системе
     */
    @ManyToOne
    @JoinColumn(name = "roles_id")
    Role role;
}