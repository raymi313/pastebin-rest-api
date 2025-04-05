package org.example.pastebinrestapi.controllers;

import lombok.RequiredArgsConstructor;
import org.example.pastebinrestapi.dto.UserDto.AuthRequestDto;
import org.example.pastebinrestapi.dto.UserDto.AuthResponseDto;
import org.example.pastebinrestapi.entities.UserEntity;
import org.example.pastebinrestapi.exceptions.BadRequestException;
import org.example.pastebinrestapi.jwt.JwtUtils;
import org.example.pastebinrestapi.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDto request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .isActive(true)
                .createdAt(Instant.now())
                .build();

        userRepository.save(user);

        String token = JwtUtils.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto request) {
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        String token = JwtUtils.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}