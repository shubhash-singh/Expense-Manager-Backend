package com.example.expensetracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensetracker.dto.LoginRequest;
import com.example.expensetracker.dto.LoginResponse;
import com.example.expensetracker.dto.SignUpRequest;
import com.example.expensetracker.model.User;
import com.example.expensetracker.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest) {
        // Store the email/password directly in MongoDB
        User user = authService.signUp(signUpRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // Return a simple message and UID if the credentials match
        LoginResponse response = authService.login(loginRequest);
        if (response.getUid() == null) {
            return ResponseEntity.status(401).body(response);
        }
        return ResponseEntity.ok(response);
    }
}

