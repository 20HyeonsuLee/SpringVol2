package com.example.BoardAPI.service;

import com.example.BoardAPI.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUser();
    Optional<User> getUserByUserId(String userId);
    void addUser(User user);
    void modifyUser(long id, User user);
    void deleteUser(long id);

}
