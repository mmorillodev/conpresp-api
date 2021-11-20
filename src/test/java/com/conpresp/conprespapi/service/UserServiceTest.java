package com.conpresp.conprespapi.service;

import com.conpresp.conprespapi.dto.UserCreateRequest;
import com.conpresp.conprespapi.dto.UserUpdateRequest;
import com.conpresp.conprespapi.entity.Profile;
import com.conpresp.conprespapi.entity.User;
import com.conpresp.conprespapi.entity.UserGroup;
import com.conpresp.conprespapi.exception.ResourceCreationException;
import com.conpresp.conprespapi.repository.GroupRepository;
import com.conpresp.conprespapi.repository.ProfileRepository;
import com.conpresp.conprespapi.repository.UserRepository;
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

    @Test
    void shouldReturnTheInsertedUserID() throws ResourceCreationException {
        var profile = new Profile("MODERATOR");
        var userGroup = new UserGroup("UAM");

        when(passwordEncoder.encode(any())).thenReturn("123456678");
        when(profileRepository.findByName("MODERATOR")).thenReturn(Optional.of(profile));
        when(groupRepository.findByName("UAM")).thenReturn(Optional.of(userGroup));
        when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        var actualID = userService.createUser(getMockedUserRequest());

        assertDoesNotThrow(() -> UUID.fromString(actualID));
    }

    @Test
    void shouldReturnUpdatedUser() throws ChangeSetPersister.NotFoundException {
        var updateRequest = new UserUpdateRequest("Name", "Last name", "other@mail.com");

        when(userRepository.findById("UUID")).thenReturn(Optional.of(new User()));

        var updatedUser = userService.updateUser("UUID", updateRequest);

        assertEquals("Name", updatedUser.getFirstName());
        assertEquals("Last name", updatedUser.getLastName());
        assertEquals("other@mail.com", updatedUser.getEmail());
    }

    private UserCreateRequest getMockedUserRequest() {
        return new UserCreateRequest(
                "Matheus",
                "Nask",
                "matheus.nask@mail.com",
                "123456789",
                "MODERATOR",
                "UAM"
        );
    }
}
