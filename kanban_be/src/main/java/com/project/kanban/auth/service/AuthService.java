package com.project.kanban.auth.service;

import com.project.kanban.auth.dto.req.LoginRequest;
import com.project.kanban.auth.dto.req.RegisterRequest;
import com.project.kanban.auth.dto.res.AuthResponse;
import com.project.kanban.auth.enums.Role;
import com.project.kanban.user.model.User;
import com.project.kanban.user.repository.UserRepository;
import com.project.kanban.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public void register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_EMPLOYEE);
        user.setStatus("ACTIVE");
        user.setActive(true);
        user.setCreateAt(java.time.LocalDateTime.now());
        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword()
                )
        );

//        User user = userRepository.findByUsername(request.getUsername())
//                .orElseThrow(() -> new RuntimeException("User not found"));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtils.generateToken(user);
        return new AuthResponse(token);
    }

}

