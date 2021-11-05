package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.dto.UserListResponse;
import com.conpresp.conprespapi.dto.UserRequest;
import com.conpresp.conprespapi.dto.UserResponse;
import com.conpresp.conprespapi.entity.User;
import com.conpresp.conprespapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(
            @Valid @RequestBody UserRequest userRequest,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var id = userService.createUser(
                new User(
                        userRequest.getName(),
                        userRequest.getEmail(),
                        userRequest.getPassword()
                )
        );

        var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public UserListResponse getAll() {
        var users = userService.getUsers();

        return new UserListResponse(
                users.stream().map(
                        user ->
                                new UserResponse(
                                        user.getId(),
                                        user.getProfile(),
                                        user.getName(),
                                        user.getEmail(),
                                        user.getStatus(),
                                        user.getCreated_at()
                                )
                ).collect(Collectors.toList()),
                users.size()
        );
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable String uuid) {
        return userService.getUserById(uuid).map(record -> {
            userService.deleteById(uuid);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
