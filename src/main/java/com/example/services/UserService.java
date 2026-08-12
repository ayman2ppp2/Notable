package com.example.services;

import com.example.Security.JwtService;
import com.example.dto.auth.AuthResponse;
import com.example.dto.auth.LoginRequest;
import com.example.dto.auth.RegisterRequest;
import com.example.models.User;
import com.example.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserService
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("username already taken");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("email already in use");
        }

        User user = new User(
            request.username(),
            request.email(),
            passwordEncoder.encode(request.password())
        );
        userRepository.save(user);

        return new AuthResponse(jwtService.generateToken(user));
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository
            .findByUsername(request.username())
            .orElseThrow(() ->
                new IllegalArgumentException("invalid username or password")
            );
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("invalid username or password");
        }

        return new AuthResponse(jwtService.generateToken(user));
    }
}
