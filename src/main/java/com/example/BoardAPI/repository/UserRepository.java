package com.example.BoardAPI.repository;

import com.example.BoardAPI.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    void deleteUserByUserId(String userId) throws EntityNotFoundException;
}
