package ru.netology.cloudservice.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Конфигурация для кодирования паролей
 *
 * @author Viktor_Loskutov
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Возвращает реализацию интерфейса для кодирования паролей
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}