package com.conpresp.conprespapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserListResponse {
    private List<UserResponse> users;
    private int total;
}
