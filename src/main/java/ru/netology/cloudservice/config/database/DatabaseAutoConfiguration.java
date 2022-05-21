package ru.netology.cloudservice.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Авто-конфигурация подключает bean AuditorAware для отслеживания
 * изменений в сущностях
 *
 * @author Viktor_Loskutov
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "dBAuditorAware")
@ConditionalOnProperty(prefix = "spring.datasource", name = "url")
@ConditionalOnMissingBean(value = AuditorAware.class)
public class DatabaseAutoConfiguration {

    @Value("${spring.application.name}")
    private String systemName;

    @Bean
    public AuditorAware<String> dBAuditorAware() {
        return new DBAuditorAware(systemName);
    }
}

