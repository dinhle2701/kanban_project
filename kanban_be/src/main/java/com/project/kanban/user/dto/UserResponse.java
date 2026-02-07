package com.project.kanban.user.dto;

import com.project.kanban.auth.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private String profileImage;
    private String userCode;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
