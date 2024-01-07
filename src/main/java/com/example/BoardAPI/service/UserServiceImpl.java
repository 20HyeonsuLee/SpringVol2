package com.example.BoardAPI.service;

import com.example.BoardAPI.domain.User;
import com.example.BoardAPI.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private static final String SECRET_KEY = "c3ByaW5nYm9vdC1qd3QtdHV0b3JpYWwtc3ByaW5nYm9vdC1qd3QtdHV0b3JpYWwtc3ByaW5nYm9vdC1qd3QtdHV0b3JpYWwK";
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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
    public String authenticateUser(String userId, String password) throws AuthenticationException {
        User user = getUserByUserId(userId).orElseThrow(() -> new AuthenticationException("User not found"));
        if (!passwordEncoder.matches(password, passwordEncoder.encode(user.getPassword()))) {
            throw new AuthenticationException("Password mismatch");
        }
        return generateToken(user);
    }

    private String generateToken(User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + 3600000; // 토큰 유효 시간 설정 (예: 1시간)
        Date exp = new Date(expMillis);
        return Jwts.builder()
                .setSubject(user.getUserId())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
