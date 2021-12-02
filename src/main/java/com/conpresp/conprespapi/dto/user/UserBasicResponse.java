package com.conpresp.conprespapi.dto.user;

import com.conpresp.conprespapi.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserBasicResponse {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String profile;

    private String status;

    public static UserBasicResponse fromUser(User user) {
        return new UserBasicResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getProfile().getName(),
                user.getStatus().name()
        );
    }
}
