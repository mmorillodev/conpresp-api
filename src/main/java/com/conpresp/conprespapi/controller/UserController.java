package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.dto.UserListResponse;
import com.conpresp.conprespapi.dto.UserRequest;
import com.conpresp.conprespapi.dto.UserResponse;
import com.conpresp.conprespapi.dto.UserUpdateRequest;
import com.conpresp.conprespapi.entity.User;
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

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, ProfileRepository profileRepository) {
        this.userService = userService;
        this.passwordEncoder  = passwordEncoder;
        this.profileRepository = profileRepository;
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserRequest userRequest,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var profile = profileRepository.findByName(userRequest.getProfile()).orElse(null);

        if (profile == null) {
            return ResponseEntity.badRequest().build();
        }

        var id = userService.createUser(
                new User(
                        profile,
                        userRequest.getFirstName(),
                        userRequest.getLastName(),
                        userRequest.getEmail(),
                        passwordEncoder.encode(userRequest.getPassword())
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
                                        user.getFirstName(),
                                        user.getLastName(),
                                        user.getEmail(),
                                        user.getStatus(),
                                        user.getCreated_at()
                                )
                ).collect(Collectors.toList()),
                users.size()
        );
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getUserByUuid(@PathVariable String uuid) {
        return userService.getUserById(uuid).map(user -> {
            var returnedUser = new UserResponse(
                    user.getId(),
                    user.getProfile(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getStatus(),
                    user.getCreated_at());
         return ResponseEntity.ok().body(returnedUser);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String uuid,
                           @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            var updatedUser = userService.updateUser(uuid, userUpdateRequest);
            return ResponseEntity.ok(new UserResponse(
                    uuid,
                    updatedUser.getProfile(),
                    updatedUser.getFirstName(),
                    updatedUser.getLastName(),
                    updatedUser.getEmail(),
                    updatedUser.getStatus(),
                    updatedUser.getCreated_at()
            ));
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
