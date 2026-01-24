package com.zimpy.common;

import java.time.LocalDateTime;

public class ApiErrorResponse {

    private boolean success = false;
    private int status;
    private String error;
    private String message;
    private String path;
    private LocalDateTime timestamp;

    public ApiErrorResponse(
            int status,
            String error,
            String message,
            String path
    ) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    // getters

    public boolean isSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
