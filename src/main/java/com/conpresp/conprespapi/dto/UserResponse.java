package com.conpresp.conprespapi.dto;

import com.conpresp.conprespapi.entity.User;
import com.conpresp.conprespapi.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    private String id;

    private String profile;

    private String userGroup;

    private String firstName;

    private String lastName;

    private String email;

    private UserStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static UserResponse fromUser(User u) {
        return new UserResponse(
                u.getId(),
                u.getProfile().getName(),
                u.getUserGroup().getName(),
                u.getFirstName(),
                u.getLastName(),
                u.getEmail(),
                u.getStatus(),
                u.getCreatedAt(),
                u.getUpdatedAt()
        );
    }
}
