package com.example.demo.response;

import com.example.demo.Entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {
    private Status status;
    private List<User> users;
}