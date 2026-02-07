package com.project.kanban.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class UserRequest {
    private String userCode;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String profileImage;
    private String status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
