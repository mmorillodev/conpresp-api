package com.conpresp.conprespapi.Specifications.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter @Setter
@Builder
public class UserSearchCriteria {

    private String name;

    private String lastName;

    private String email;

    private String profile;

    private String status;

    private String group;
}
