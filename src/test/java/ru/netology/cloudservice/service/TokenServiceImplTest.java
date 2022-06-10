package ru.netology.cloudservice.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import ru.netology.cloudservice.api.dto.TokenDto;
import ru.netology.cloudservice.api.dto.UserDto;
import ru.netology.cloudservice.api.dto.WarningMessage;
import ru.netology.cloudservice.config.security.jwt.JwtProvider;
import ru.netology.cloudservice.domain.User;
import ru.netology.cloudservice.exception.ValidationException;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
class TokenServiceImplTest {

    @InjectMocks
    @Spy
    TokenServiceImpl subj;

    @Mock
    UserService userService;

    @Mock
    JwtProvider jwtProvider;

    private InOrder inOrder;

    @BeforeEach
    void setup() {
        inOrder = inOrder(subj, userService, jwtProvider);
    }

    @Test
    void getTokenSuccess() {
        var userDto = getRandomUserDto();
        var user = new User()
                .setLogin(userDto.getLogin())
                .setPassword(getRandomUserDto().getPassword());

        var tokenString = randomString();
        var expected = new TokenDto().setAccessToken(tokenString);

        doReturn(user).when(userService).findByLoginAndPassword(userDto.getLogin(), userDto.getPassword());
        doReturn(tokenString).when(jwtProvider).generateToken(user.getLogin());

        var actual = subj.getToken(userDto);

        assertEquals(expected.getAccessToken(), actual.getAccessToken());

        inOrder.verify(userService, times(1)).findByLoginAndPassword(userDto.getLogin(), userDto.getPassword());
        inOrder.verify(jwtProvider, times(1)).generateToken(user.getLogin());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void getTokenFail() {
        var userDto = getRandomUserDto();
        var user = new User()
                .setLogin(userDto.getLogin())
                .setPassword(getRandomUserDto().getPassword());

        var expected = new WarningMessage("tokenService", "Не верный логин или пароль");

        doReturn(null).when(userService).findByLoginAndPassword(userDto.getLogin(), userDto.getPassword());

        var actual = assertThrows(ValidationException.class, () -> subj.getToken(userDto));

        assertAll(
                () -> {
                    assertEquals(actual.getWarning().getSystemName(), expected.getSystemName());
                    assertEquals(actual.getWarning().getDisplayMessage(), expected.getDisplayMessage());
                }
        );

        inOrder.verify(userService, times(1)).findByLoginAndPassword(userDto.getLogin(), userDto.getPassword());
        inOrder.verify(jwtProvider, never()).generateToken(user.getLogin());
        inOrder.verifyNoMoreInteractions();
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