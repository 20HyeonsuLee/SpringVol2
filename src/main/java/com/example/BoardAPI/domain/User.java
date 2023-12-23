package com.example.BoardAPI.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class User {
    private String userId;
    private String username;
    private String password;
    public User(String userId, String password, String username) {
        this.userId = userId;
        this.password = password;
        this.username = username;
    }
}