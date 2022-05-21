package ru.netology.cloudservice.config.database;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * DBAuditorAware служит для проставления в сущности, кем была изменена.
 *
 * @author Viktor_Loskutov
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DBAuditorAware implements AuditorAware<String> {

    @Value("${spring.application.name}")
    String systemName;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(systemName);
    }
}