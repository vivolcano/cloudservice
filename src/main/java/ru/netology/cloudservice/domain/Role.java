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
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Представление сущности роли в системе
 *
 * @author Viktor_Loskutov
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "roles")
@Entity
public class Role extends CreateAtIdentified implements Identified<Integer> {

    /**
     * Уникальный идентификатор роли
     */
    @Id
    @Column(nullable = false)
    Integer id;

    /**
     * Наименование роли
     */
    @Column(length = 20, nullable = false)
    String name;
}