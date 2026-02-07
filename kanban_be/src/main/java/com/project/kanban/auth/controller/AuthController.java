package com.project.kanban.auth.controller;

import com.project.kanban.auth.dto.req.LoginRequest;
import com.project.kanban.auth.dto.req.RegisterRequest;
import com.project.kanban.auth.dto.res.AuthResponse;
import com.project.kanban.auth.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(
        name = "api_auth", // 👈 tên bạn muốn hiển thị
        description = "API xử lý các nghiệp vụ liên quan đến xác thực"
)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Register success");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}

