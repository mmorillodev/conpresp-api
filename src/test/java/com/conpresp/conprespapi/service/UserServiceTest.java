package com.conpresp.conprespapi.service;

import com.conpresp.conprespapi.dto.UserRequest;
import com.conpresp.conprespapi.dto.UserUpdateRequest;
import com.conpresp.conprespapi.entity.Profile;
import com.conpresp.conprespapi.entity.User;
import com.conpresp.conprespapi.entity.UserGroup;
import com.conpresp.conprespapi.exception.ResourceCreationException;
import com.conpresp.conprespapi.repository.GroupRepository;
import com.conpresp.conprespapi.repository.ProfileRepository;
import com.conpresp.conprespapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private ProfileRepository profileRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setupBasicMocks() {
        var user = getMockedUser();
        when(userRepository.save(any())).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("123456678");
        when(profileRepository.findByName(any())).thenReturn(Optional.of(new Profile("MODERATOR")));
        when(groupRepository.findByName(any())).thenReturn(Optional.of(new UserGroup("UAM")));
    }

    @Test
    void shouldReturnTheInsertedUserID() throws ResourceCreationException {
        var actualID = userService.createUser(getMockedUserRequest());

        assertDoesNotThrow(() -> UUID.fromString(actualID));
    }

    @Test
    void shouldReturnUpdatedUser() throws ChangeSetPersister.NotFoundException, ResourceCreationException {
        var updateRequest = new UserUpdateRequest("Matheus", "Morillo", "nask@gmeil.com");

        when(userRepository.findById(any())).thenReturn(Optional.of(getMockedUser()));

        var actualID = userService.createUser(getMockedUserRequest());
        var updatedUser = userService.updateUser(actualID, updateRequest);

        assertEquals("Matheus", updatedUser.getFirstName());
    }

    private User getMockedUser() {
        var user = new User();
        user.setId(UUID.randomUUID().toString());

        return user;
    }

    private UserRequest getMockedUserRequest() {
        return new UserRequest(
                "Matheus",
                "Nask",
                "matheus.nask@mail.com",
                "123456789",
                "MODERATOR",
                "UAM"
        );
    }
}
