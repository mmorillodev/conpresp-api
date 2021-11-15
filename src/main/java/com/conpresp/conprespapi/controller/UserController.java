package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.dto.UserListResponse;
import com.conpresp.conprespapi.dto.UserRequest;
import com.conpresp.conprespapi.dto.UserResponse;
import com.conpresp.conprespapi.dto.UserUpdateRequest;
import com.conpresp.conprespapi.exception.ResourceCreationException;
import com.conpresp.conprespapi.repository.GroupRepository;
import com.conpresp.conprespapi.repository.ProfileRepository;
import com.conpresp.conprespapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private GroupRepository groupRepository;

    @PostMapping
    public ResponseEntity<Void> createUser(
            @Valid @RequestBody UserRequest userRequest,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        try {
            var id = userService.createUser(userRequest);

            var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(uri).build();

        } catch (ResourceCreationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public UserListResponse getAll() {
        var users = userService.getUsers();

        return new UserListResponse(
                users.stream().map(UserResponse::fromUser).collect(Collectors.toList()),
                users.size()
        );
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getUserByUuid(@PathVariable String uuid) {
        return userService.getUserById(uuid).map(user ->
                ResponseEntity.ok().body(UserResponse.fromUser(user))
        ).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String uuid,
                                                   @Valid @RequestBody UserUpdateRequest userUpdateRequest) {

        try {
            var updatedUser = userService.updateUser(uuid, userUpdateRequest);
            return ResponseEntity.ok(UserResponse.fromUser(updatedUser));
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable String uuid) {
        return userService.getUserById(uuid).map(user -> {
            userService.deleteById(uuid);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
