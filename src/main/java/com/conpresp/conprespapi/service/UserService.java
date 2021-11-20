package com.conpresp.conprespapi.service;

import com.conpresp.conprespapi.dto.UserRequest;
import com.conpresp.conprespapi.dto.UserUpdateRequest;
import com.conpresp.conprespapi.entity.User;
import com.conpresp.conprespapi.exception.ResourceCreationException;
import com.conpresp.conprespapi.repository.GroupRepository;
import com.conpresp.conprespapi.repository.ProfileRepository;
import com.conpresp.conprespapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createUser(UserRequest userRequest, String token) throws ResourceCreationException {
        var profile = profileRepository.findByName(userRequest.getProfile()).orElse(null);
        var group = groupRepository.findByName(userRequest.getUserGroup()).orElse(null);

        var loggedUserID = tokenService.getUserId(token);
        var loggedUser = userRepository.findById(loggedUserID).orElse(null);

        if (profile == null || group == null || loggedUser == null) {
            throw new ResourceCreationException();
        }

        var createdUser = userRepository.save(userRequest.toUser(passwordEncoder, profile, group, loggedUser, loggedUser));

        return createdUser.getId();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String uuid) {
        return userRepository.findById(uuid);
    }

    public void deleteById(String uuid) {
        userRepository.deleteById(uuid);
    }

    public User updateUser(String uuid, UserUpdateRequest userUpdateRequest) throws ChangeSetPersister.NotFoundException {
        var user = userRepository.findById(uuid).orElseThrow(ChangeSetPersister.NotFoundException::new);
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setEmail(userUpdateRequest.getEmail());
        user.setUpdated_at(LocalDateTime.now());

        userRepository.save(user);

        return user;
    }
}
