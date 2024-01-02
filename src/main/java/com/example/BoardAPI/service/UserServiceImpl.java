package com.example.BoardAPI.service;

import com.example.BoardAPI.domain.User;
import com.example.BoardAPI.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
    @Override
    public Optional<User> getUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }
    @Override
    public void modifyUser(long id, User userToUpdate) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        user.setUserId(userToUpdate.getUserId());
        user.setPassword(userToUpdate.getPassword());
        user.setUserName(userToUpdate.getUserName());
        userRepository.save(user);
    }
    @Override
    public void deleteUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }
}
