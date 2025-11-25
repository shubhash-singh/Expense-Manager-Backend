package com.example.expensetracker.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.expensetracker.dto.LoginRequest;
import com.example.expensetracker.dto.LoginResponse;
import com.example.expensetracker.dto.SignUpRequest;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(SignUpRequest signUpRequest) {
        // Just store the email and password as provided (not secure, but requested)
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setUid(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        return userRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
                .map(existingUser -> {
                    // Ensure we have a uid for older records
                    if (existingUser.getUid() == null || existingUser.getUid().isEmpty()) {
                        existingUser.setUid(UUID.randomUUID().toString());
                        userRepository.save(existingUser);
                    }
                    String uid = existingUser.getUid();
                    return new LoginResponse("Login successful", uid, uid);
                })
                .orElseGet(() -> new LoginResponse("Invalid email or password", null, null));
    }
}

