package com.project.kanban.user.service;

import com.project.kanban.user.dto.UserRequest;
import com.project.kanban.user.dto.UserResponse;
import com.project.kanban.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
//    List<User> getUsers();
//    UserResponse updateUser(UserRequest userRequest);
//    void deleteUser(Long userId);

    Page<UserResponse> getUsers(Pageable pageable);

    Optional<UserResponse> getById(Long id);

    UserResponse updateAccount(Long id, UserRequest userRequest);

    void deleteAccount(Long id);

    Optional<UserResponse> checkStatus(Long id);

    UserResponse lockUser(Long id);
}
