package ru.netology.cloudservice.config.database.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.util.Objects;

/**
 * Конвертер для java типа Instant.
 * Преобразует Instant в Long и обратно.
 *
 * @author Viktor_Loskutov
 */
@Converter(autoApply = true)
public class InstantAttributeConverter implements AttributeConverter<Instant, Long> {

    @Override
    public Long convertToDatabaseColumn(Instant attribute) {
        return (!Objects.isNull(attribute)) ? attribute.toEpochMilli() : null;
    }

    @Override
    public Instant convertToEntityAttribute(Long value) {
        return (!Objects.isNull(value)) ? Instant.ofEpochMilli(value) : null;
    }
}