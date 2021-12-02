package com.conpresp.conprespapi.dto.user;

import com.conpresp.conprespapi.entity.user.User;
import com.conpresp.conprespapi.entity.user.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailResponse {
    private String id;

    private String profile;

    private String userGroup;

    private String firstName;

    private String lastName;

    private String email;

    private String status;

    private String createdBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private String updatedBy;

    public static UserDetailResponse fromUser(User u) {
        return new UserDetailResponse(
                u.getId(),
                u.getProfile().getName(),
                u.getUserGroup().getName(),
                u.getFirstName(),
                u.getLastName(),
                u.getEmail(),
                u.getStatus(),
                u.getCreatedBy(),
                u.getCreatedAt(),
                u.getUpdatedAt(),
                u.getUpdatedBy()
        );
    }
}
