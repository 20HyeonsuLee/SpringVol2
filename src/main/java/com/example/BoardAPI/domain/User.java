package com.example.BoardAPI.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "userId")
    private String userId;
    @Column(name = "password")
    private String password;
    @Column(name = "userName")
    private String userName;
    @Builder
    public User(Integer id, String userId, String password, String userName) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.userName = userName;
    }
}
