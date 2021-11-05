package com.conpresp.conprespapi.service;

import com.conpresp.conprespapi.entity.User;
import com.conpresp.conprespapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
