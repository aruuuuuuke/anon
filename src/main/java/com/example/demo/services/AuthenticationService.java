package com.example.demo.services;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.AuthenticationRequest;
import com.example.demo.security.AuthenticationResponse;
import com.example.demo.security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user;

        if (request.getRole().equals("USER")) {
            user = User.builder()
                    .username("anonimus_" + UUID.randomUUID().toString().substring(0, 8)) // Генерация username
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
        } else if (request.getRole().equals("MANAGER")) {
            validateEmail(request.getEmail());
            validateUsername(request.getUsername());

            user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.MANAGER)
                    .managerCode(UUID.randomUUID().toString().substring(0, 6))
                    .build();
        } else {
            throw new IllegalArgumentException("Invalid role selected");
        }
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        AuthenticationResponse build = AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        return build;
    }

    private void validateEmail(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
    }

    private void validateUsername(String username) {
        if (repository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already in use");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
