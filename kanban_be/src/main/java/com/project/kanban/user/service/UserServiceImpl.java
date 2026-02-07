package com.project.kanban.user.service;

import com.project.kanban.exception.custom.ResourceNotFoundException;
import com.project.kanban.user.dto.UserRequest;
import com.project.kanban.user.dto.UserResponse;
import com.project.kanban.user.model.User;
import com.project.kanban.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .userCode(user.getUserCode())
                        .phoneNumber(user.getPhoneNumber())
                        .address(user.getAddress())
                        .profileImage(user.getProfileImage())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .status(user.getStatus())
                        .createAt(user.getCreateAt())
                        .updateAt(user.getUpdateAt())
                        .build());
    }

    @Override
    public Optional<UserResponse> getById(Long id) {
        return userRepository.findById(id)
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .userCode(user.getUserCode())
                        .phoneNumber(user.getPhoneNumber())
                        .address(user.getAddress())
                        .profileImage(user.getProfileImage())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .status(user.getStatus())
                        .createAt(user.getCreateAt())
                        .updateAt(user.getUpdateAt())
                        .build());
    }

    @Override
    public UserResponse updateAccount(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Account not found with id: " + id,
                        "/api/v1/account/" + id
                )
        );

        // ✅ kiểm tra và cập nhật từng field nếu có thay đổi
        if (userRequest.getFullName() != null
                && !userRequest.getFullName().equals(user.getFullName())) {
            user.setFullName(userRequest.getFullName());
        }

        if (userRequest.getEmail() != null
                && !userRequest.getEmail().equals(user.getEmail())) {
            user.setEmail(userRequest.getEmail());
        }

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        if (userRequest.getPhoneNumber() != null
                && !userRequest.getPhoneNumber().equals(user.getPhoneNumber())) {
            user.setPhoneNumber(userRequest.getPhoneNumber());
        }

        if (userRequest.getAddress() != null
                && !userRequest.getAddress().equals(user.getAddress())) {
            user.setAddress(userRequest.getAddress());
        }

        if (userRequest.getProfileImage() != null
                && !userRequest.getProfileImage().equals(user.getProfileImage())) {
            user.setProfileImage(userRequest.getProfileImage());
        }

        // cập nhật thời gian
        user.setUpdateAt(LocalDateTime.now());

        User updated = userRepository.save(user);

        return UserResponse.builder()
                .id(updated.getId())
                .username(updated.getUsername())
                .userCode(updated.getUserCode())
                .phoneNumber(updated.getPhoneNumber())
                .address(updated.getAddress())
                .profileImage(updated.getProfileImage())
                .email(updated.getEmail())
                .role(updated.getRole())
                .createAt(updated.getCreateAt())
                .updateAt(updated.getUpdateAt())
                .build();
    }

    @Override
    public void deleteAccount(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public Optional<UserResponse> checkStatus(Long id) {
        return userRepository.findById(id)
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .userCode(user.getUserCode())
                        .phoneNumber(user.getPhoneNumber())
                        .address(user.getAddress())
                        .profileImage(user.getProfileImage())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .createAt(user.getCreateAt())
                        .updateAt(user.getUpdateAt())
                        .build());
    }

    @Override
    public UserResponse lockUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Account not found with id: " + id,
                        "/api/v1/account/" + id
                )
        );

        user.setStatus("LOCKED");

        // cập nhật thời gian
        user.setUpdateAt(LocalDateTime.now());

        User updated = userRepository.save(user);

        return UserResponse.builder()
                .id(updated.getId())
                .status(updated.getStatus())
                .updateAt(updated.getUpdateAt())
                .build();
    }


}
