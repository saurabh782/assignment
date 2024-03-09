package com.example.demo.entry;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntry {
    private Long userId;
    private String emailId;
    private String name;
    private String password;
    private String token;
}
