package com.conpresp.conprespapi.service;

import com.conpresp.conprespapi.entity.User;
import com.conpresp.conprespapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        assertEquals("ID", actualID);
    }

    private User getMockedUser() {
        var user = new User();
        user.setId("ID");

        return user;
    }
}