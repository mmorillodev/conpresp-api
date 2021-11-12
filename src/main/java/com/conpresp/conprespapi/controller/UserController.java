package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.dto.UserListResponse;
import com.conpresp.conprespapi.dto.UserRequest;
import com.conpresp.conprespapi.dto.UserResponse;
import com.conpresp.conprespapi.dto.UserUpdateRequest;
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

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, ProfileRepository profileRepository, GroupRepository groupRepository) {
        this.userService = userService;
        this.passwordEncoder  = passwordEncoder;
        this.profileRepository = profileRepository;
        this.groupRepository = groupRepository;
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserRequest userRequest,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var profile = profileRepository.findByName(userRequest.getProfile()).orElse(null);
        var group = groupRepository.findByName(userRequest.getUserGroup()).orElse(null);

        if (profile == null) {
            return ResponseEntity.badRequest().build();
        }

        var id = userService.createUser(userRequest.toUser(passwordEncoder, profile, group));

        var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
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
        return userService.getUserById(uuid).map(user -> {
         return ResponseEntity.ok().body(UserResponse.fromUser(user));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String uuid,
                           @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        var group = groupRepository.findByName(userUpdateRequest.getGroup()).orElse(null);

        try {
            var updatedUser = userService.updateUser(uuid, group, userUpdateRequest);
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
