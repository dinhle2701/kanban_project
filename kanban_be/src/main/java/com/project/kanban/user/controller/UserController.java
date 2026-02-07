package com.project.kanban.user.controller;

import com.project.kanban.user.dto.UserRequest;
import com.project.kanban.user.dto.UserResponse;
import com.project.kanban.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(
        name = "api_user", // 👈 tên bạn muốn hiển thị
        description = "API xử lý các nghiệp vụ liên quan đến tài khoản"
)
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(
            summary = "Get all hostel",
            description = "Lấy danh sách toàn bộ nhà trọ",
            extensions = @Extension(properties = @ExtensionProperty(name = "x-order", value = "0"))
    )
    @GetMapping("")
    public ResponseEntity<Page<UserResponse>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UserResponse> userRes = userService.getUsers(pageable);

        return new ResponseEntity<>(userRes, HttpStatus.OK);
    }

    @Operation(
            summary = "Get hostel by id",
            description = "Lấy thông tin nhà trọ theo id",
            extensions = @Extension(properties = @ExtensionProperty(name = "x-order", value = "1"))
    )
    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserResponse>> getAccountById(@PathVariable Long id){
        Optional<UserResponse> accountRes = userService.getById(id);
        return new ResponseEntity<>(accountRes, HttpStatus.OK);
    }

    @Operation(
            summary = "Update hostel",
            description = "Tạo mới nhà trọ",
            extensions = @Extension(properties = @ExtensionProperty(name = "x-order", value = "2"))
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateAccountById(@PathVariable Long id, @RequestBody UserRequest userRequest){
        UserResponse accountRes = userService.updateAccount(id, userRequest);
        return new ResponseEntity<>(accountRes, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete hostel",
            description = "Tạo mới nhà trọ",
            extensions = @Extension(properties = @ExtensionProperty(name = "x-order", value = "3"))
    )
    @DeleteMapping("/{id}")
    public void deleteAccountById(@PathVariable Long id){
        userService.deleteAccount(id);
    }

    @Operation(
            summary = "Check hostel",
            description = "Tạo mới nhà trọ",
            extensions = @Extension(properties = @ExtensionProperty(name = "x-order", value = "4"))
    )
    @GetMapping("/status/{id}")
    public ResponseEntity<Optional<UserResponse>> checkStatus(@PathVariable Long id){
        Optional<UserResponse> accountRes = userService.checkStatus(id);
        return new ResponseEntity<>(accountRes, HttpStatus.OK);
    }

    @Operation(
            summary = "Lock hostel",
            description = "Tạo mới nhà trọ",
            extensions = @Extension(properties = @ExtensionProperty(name = "x-order", value = "5"))
    )
    @PutMapping("/lock/{id}")
    public ResponseEntity<UserResponse> lockUser(@PathVariable Long id){
        UserResponse accountRes = userService.lockUser(id);
        return new ResponseEntity<>(accountRes, HttpStatus.OK);
    }
}
