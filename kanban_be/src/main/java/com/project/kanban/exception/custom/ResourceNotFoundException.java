package com.project.kanban.exception.custom;

import java.time.LocalDateTime;

public class ResourceNotFoundException extends RuntimeException {

    private final LocalDateTime timestamp;
    private final String path;
    private final Integer code;

    public ResourceNotFoundException(String message, String path) {
        super(message);
        this.timestamp = LocalDateTime.now();
        this.path = path;
        this.code = 404;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public String getPath() { return path; }
    public Integer getCode() { return code; }
}
