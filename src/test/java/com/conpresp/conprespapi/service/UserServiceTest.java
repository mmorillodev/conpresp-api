package com.conpresp.conprespapi.service;

import com.conpresp.conprespapi.entity.User;
import com.conpresp.conprespapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldReturnTheInsertedUserID() {
        var user = getMockedUser();
        when(userRepository.save(any())).thenReturn(user);

        var actualID = userService.createUser(user);

        assertDoesNotThrow(() -> UUID.fromString(actualID));
    }

    private User getMockedUser() {
        var user = new User();
        user.setId(UUID.randomUUID().toString());

        return user;
    }
}