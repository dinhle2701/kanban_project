package com.project.kanban.user.model;

import com.project.kanban.auth.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "userCode")
    private String userCode;

    @Column(name = "username")
    private String username;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "address")
    private String address;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "profileImage")
    private String profileImage;

    @Column(name = "status")
    private String status;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

}


