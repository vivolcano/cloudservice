package ru.netology.cloudservice.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import ru.netology.cloudservice.api.dto.UserDto;
import ru.netology.cloudservice.api.dto.WarningMessage;
import ru.netology.cloudservice.domain.User;
import ru.netology.cloudservice.exception.ValidationException;
import ru.netology.cloudservice.repository.UserRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith({MockitoExtension.class})
class UserServiceImplTest {

    @InjectMocks
    @Spy
    UserServiceImpl subj;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    private InOrder inOrder;

    @BeforeEach
    void setup() {
        inOrder = inOrder(subj, userRepository, passwordEncoder);
    }

    @Test
    void findByLoginSuccess() {
        var login = randomString();
        var expected = new User()
                .setLogin(login)
                .setPassword(randomString());

        doReturn(expected).when(userRepository).findByLogin(login);

        var actual = subj.findByLogin(login);

        assertEquals(expected, actual);

        inOrder.verify(userRepository, times(1)).findByLogin(login);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void findByLoginFail() {
        var login = randomString();

        var expected = new WarningMessage("cloudService", "Логин или пароль не найден");

        doReturn(null).when(userRepository).findByLogin(login);

        var actual = assertThrows(ValidationException.class, () -> subj.findByLogin(login));

        assertAll(
                () -> {
                    assertEquals(actual.getWarning().getSystemName(), expected.getSystemName());
                    assertEquals(actual.getWarning().getDisplayMessage(), expected.getDisplayMessage());
                }
        );

        inOrder.verify(userRepository, times(1)).findByLogin(login);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void findByLoginAndPasswordSuccess() {
        var login = randomString();
        var password = randomString();
        var encodePassword = randomString();
        var expected = new User()
                .setLogin(login)
                .setPassword(encodePassword);

        doReturn(expected).when(userRepository).findByLogin(login);
        doReturn(expected).when(subj).passwordMatches(password, expected);

        var actual = subj.findByLoginAndPassword(login, password);

        assertEquals(expected, actual);

        inOrder.verify(userRepository, times(1)).findByLogin(login);
        inOrder.verify(subj, times(1)).passwordMatches(password, expected);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void findByLoginAndPasswordFail() {
        var login = randomString();
        var password = randomString();
        var user = new User();

        var expected = new WarningMessage("cloudService", "Логин или пароль не найден");

        doReturn(null).when(userRepository).findByLogin(login);

        var actual = assertThrows(ValidationException.class, () -> subj.findByLoginAndPassword(login, password));

        assertAll(
                () -> {
                    assertEquals(actual.getWarning().getSystemName(), expected.getSystemName());
                    assertEquals(actual.getWarning().getDisplayMessage(), expected.getDisplayMessage());
                }
        );

        inOrder.verify(userRepository, times(1)).findByLogin(login);
        inOrder.verify(subj, never()).passwordMatches(password, user);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void passwordMatchesSuccess() {
        var password = randomString();
        var expected = new User().setPassword(password);

        doReturn(true).when(passwordEncoder).matches(password, expected.getPassword());

        var actual = subj.passwordMatches(password, expected);

        assertEquals(expected, actual);
    }

    @Test
    void passwordMatchesFail() {
        var password = randomString();
        var expected = new User().setPassword(null);

        doReturn(false).when(passwordEncoder).matches(password, expected.getPassword());

        var actual = subj.passwordMatches(password, expected);

        assertNull(actual);
    }

    /**
     * Получить случайного пользователя UserDto
     *
     * @return случайный пользователь
     */
    private UserDto getRandomUserDto() {
        return new UserDto()
                .setLogin(randomString())
                .setPassword(randomString());
    }

    /**
     * Получить случайную строку длинной 10 символов
     *
     * @return случайная строка
     */
    private String randomString() {
        return RandomStringUtils.random(10, true, false);
    }
}