package com.conpresp.conprespapi.service;

import com.conpresp.conprespapi.dto.UserUpdateRequest;
import com.conpresp.conprespapi.entity.Profile;
import com.conpresp.conprespapi.entity.User;
import com.conpresp.conprespapi.entity.UserGroup;
import com.conpresp.conprespapi.repository.GroupRepository;
import com.conpresp.conprespapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnTheInsertedUserID() {
        var user = getMockedUser();
        when(userRepository.save(any())).thenReturn(user);

        var actualID = userService.createUser(user);

        assertDoesNotThrow(() -> UUID.fromString(actualID));
    }

    @Test
    void shouldReturnUpdatedUser() throws ChangeSetPersister.NotFoundException {
        var user = getMockedUser();
        var updateRequest = new UserUpdateRequest("Matheus", "Morillo", "nask@gmeil.com", "DHP");

        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        var actualID = userService.createUser(user);
        var updatedUser = userService.updateUser(actualID, new UserGroup("UAM"), updateRequest);

        assertEquals("Matheus", updatedUser.getFirstName());
        assertEquals("UAM", updatedUser.getUserGroup().getName());
    }

    private User getMockedUser() {
        var user = new User();
        user.setId(UUID.randomUUID().toString());

        return user;
    }
}
