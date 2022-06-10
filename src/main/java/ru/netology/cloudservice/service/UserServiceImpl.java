package ru.netology.cloudservice.service;

import ru.netology.cloudservice.domain.User;
import ru.netology.cloudservice.exception.ValidationException;
import ru.netology.cloudservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Реализация сервиса для работы с пользователями {@link AttachmentService}
 *
 * @author Viktor_Loskutov
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByLogin(String login) {
        return Optional.ofNullable(userRepository.findByLogin(login))
                .orElseThrow(() -> new ValidationException("cloudService", "Логин или пароль не найден"));
    }

    public User findByLoginAndPassword(String login, String password) {
        return Optional.ofNullable(userRepository.findByLogin(login))
                .map(user -> passwordMatches(password, user))
                .orElseThrow(() -> new ValidationException("cloudService", "Логин или пароль не найден"));
    }

    /**
     * Метод проверяет, что закодированный пароль, полученный из БД,
     * совпадает с отправленным незакодированным паролем после того, как он также будет закодирован.
     *
     * @param password незакодированный пароль для кодирования и сопоставления
     * @param user     пользователь, полученный из БД, чей закодированный пароль из хранилища будет сопоставляться
     * @return пользователь, полученный из БД, если пароли совпадают, если нет - null.
     */
    User passwordMatches(String password, User user) {
        return passwordEncoder.matches(password, user.getPassword()) ? user : null;
    }
}