package com.conpresp.conprespapi.dto;

import com.conpresp.conprespapi.entity.UserGroup;
import com.conpresp.conprespapi.entity.Profile;
import com.conpresp.conprespapi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 32)
    private String password;

    @NotBlank @Pattern(regexp = "MODERATOR|ADMINISTRATOR|COMMON", message = "Invalid profile name! Options: MODERATOR, ADMINISTRATOR, COMMON")
    private String profile;

    @NotBlank @Pattern(regexp = "UAM|DHP|CONPRESP", message = "Invalid Group name! Options: UAM, DHP, CONPRESP")
    private String userGroup;

    public User toUser(PasswordEncoder passwordEncoder, Profile profile, UserGroup userGroup) {
        return new User(
                profile,
                userGroup,
                this.getFirstName(),
                this.getLastName(),
                this.getEmail(),
                passwordEncoder.encode(this.getPassword())
        );
    }
}
