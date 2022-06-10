package ru.netology.cloudservice.config.database;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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

    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        var name = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .orElse(systemName);
        return Optional.of(name);
    }
}
