package com.conpresp.conprespapi.Specifications.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter @Setter
@Builder
public class UserSearchCriteria {

    private Optional<String> name;

    private Optional<String> lastName;

    private Optional<String> email;

    private Optional<String> profile;

    private Optional<String> status;
}
