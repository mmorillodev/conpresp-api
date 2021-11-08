package com.conpresp.conprespapi.dto;

import com.conpresp.conprespapi.entity.Profile;
import com.conpresp.conprespapi.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class UserResponse {
    private String id;

    private Profile profile;

    private String name;

    private String email;

    private UserStatus status;

    private LocalDateTime created_at;
}