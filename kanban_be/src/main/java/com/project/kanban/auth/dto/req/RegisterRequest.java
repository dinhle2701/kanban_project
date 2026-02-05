package com.project.kanban.auth.dto.req;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String fullname;
    private String email;
    private String password;
}

