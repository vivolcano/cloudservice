package ru.netology.cloudservice.config.security;

import ru.netology.cloudservice.exception.AuthorizationException;
import ru.netology.cloudservice.exception.ValidationException;
import ru.netology.cloudservice.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Реализация сервиса {@link UserDetailsService}, загружающего пользовательские данные
 *
 * @author Viktor_Loskutov
 */
@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEntity = Optional.of(userService.findByLogin(username))
                .orElseThrow(() -> new AuthorizationException("cloudService", "Не верный логин или пароль"));
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }
}