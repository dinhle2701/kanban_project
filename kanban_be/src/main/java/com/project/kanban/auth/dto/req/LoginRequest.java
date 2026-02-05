package com.project.kanban.auth.dto.req;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}

