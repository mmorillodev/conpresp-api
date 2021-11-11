package com.conpresp.conprespapi.service;

import com.conpresp.conprespapi.dto.UserUpdateRequest;
import com.conpresp.conprespapi.entity.User;
import com.conpresp.conprespapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String createUser(User user) {
        var createdUser = userRepository.save(user);

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
