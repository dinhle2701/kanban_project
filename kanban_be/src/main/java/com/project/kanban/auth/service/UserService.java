package com.project.kanban.auth.service;

import com.project.kanban.auth.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getUsers();
    User updateUser(User user);
    void deleteUser(Long userId);
}
