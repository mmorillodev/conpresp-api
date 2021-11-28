package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.dto.error.ErrorResponse;
import com.conpresp.conprespapi.dto.user.*;
import com.conpresp.conprespapi.exception.NotEqualsException;
import com.conpresp.conprespapi.exception.PasswordInUseException;
import com.conpresp.conprespapi.exception.ResourceCreationException;
import com.conpresp.conprespapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserCreateRequest userCreateRequest,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        try {
            var id = userService.createUser(userCreateRequest);

            var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(uri).build();

        } catch (ResourceCreationException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotEqualsException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The given password are not the same."));
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

    @PutMapping("/{uuid}/change-password")
    public ResponseEntity<?> updatePassword(@PathVariable String uuid,
                                            @Valid @RequestBody UserPasswordRequest userPasswordRequest) {
        try {
            var updatedUser = userService.updatePassword(uuid, userPasswordRequest);
            return ResponseEntity.ok(UserResponse.fromUser(updatedUser));
        }catch (ChangeSetPersister.NotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (NotEqualsException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The given password are not the same."));
        } catch (PasswordInUseException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The new password cannot be the same as the current password."));
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
