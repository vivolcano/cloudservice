package ru.netology.cloudservice.domain;

import java.io.Serializable;

/**
 * Интерфейс идентифицируемых объектов
 *
 * @author Viktor_Loskutov
 */
public interface Identified<PK extends Serializable> extends Serializable {

    /**
     * Возвращает идентификатор объекта
     */
    PK getId();
}