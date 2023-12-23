package com.example.BoardAPI.Controller;

import com.example.BoardAPI.domain.User;
import com.example.BoardAPI.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UserRepository userRepository = new UserRepository();

    @GetMapping
    public ResponseEntity<List<User>> allUser() throws SQLException {
        return ResponseEntity.ok(userRepository.allUser());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findById(@PathVariable String userId) throws SQLException {
        return ResponseEntity.ok(userRepository.findById(userId));
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) throws SQLException {
        userRepository.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserName(@PathVariable String userId, @RequestBody String username) throws SQLException {
        userRepository.updateUsername(userId, username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) throws SQLException {
        System.out.println(userId);
        userRepository.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}