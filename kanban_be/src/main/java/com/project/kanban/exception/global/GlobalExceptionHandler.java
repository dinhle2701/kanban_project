package com.project.kanban.exception.global;

import java.time.LocalDateTime;

import com.project.kanban.exception.custom.AccessDeniedException;
import com.project.kanban.exception.custom.ResourceNotFoundException;
import com.project.kanban.exception.response.ExceptionResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 🔸 1. Lỗi phân quyền
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDenied(AccessDeniedException ex) {
        ExceptionResponse response = new ExceptionResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // 🔸 2. Lỗi tài nguyên không tìm thấy (tự custom)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 🔸 3. Lỗi vi phạm ràng buộc (VD: trùng khóa, unique constraint)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setCode(HttpStatus.CONFLICT.value());
        response.setMessage("Dữ liệu bị trùng hoặc vi phạm ràng buộc");
        response.setPath(request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // 🔸 4. Lỗi không tìm thấy endpoint (đường dẫn sai)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(NoHandlerFoundException ex, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setCode(HttpStatus.NOT_FOUND.value());
        response.setMessage("Không tìm thấy endpoint: " + request.getRequestURI());
        response.setPath(request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 🔸 5. Lỗi validation (nhập sai định dạng, thiếu trường...)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldError() != null
                ? ex.getBindingResult().getFieldError().getDefaultMessage()
                : "Dữ liệu không hợp lệ";
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(message);
        response.setPath(request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 🔸 6. Lỗi chung (bắt mọi exception còn lại)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGeneral(Exception ex, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
